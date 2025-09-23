
document.addEventListener('DOMContentLoaded', async () => {

  // =====================================================================================
  // STEP 1: 필수 데이터 동적 로드 (Fetch API 사용)
  // =====================================================================================

  async function fetchData() {
    try {
      const manifestResponse = await fetch('file-manifest.json');
      if (!manifestResponse.ok) throw new Error('Could not load file-manifest.json');
      const fileManifest = await manifestResponse.json();

      const [colorsResponse, linksResponse] = await Promise.all([
        fetch('colors.json'),
        fetch('links.txt')
      ]);

      if (!colorsResponse.ok || !linksResponse.ok) {
        throw new Error('Could not load config or links files.');
      }

      const colorsData = await colorsResponse.json();
      const linksData = await linksResponse.text();

      const fileContents = await Promise.all(
        fileManifest.map(path =>
          fetch(path)
            .then(res => res.ok ? res.text() : Promise.reject(`Failed to load ${path}`))
        )
      );

      const fileSystemData = fileManifest.map((path, index) => ({
        path: path,
        content: fileContents[index]
      }));

      return { fileSystemData, linksData, colorsData };

    } catch (error) {
      console.error('Error fetching data:', error);
      alert('데이터를 불러오는 데 실패했습니다. 로컬 웹 서버가 실행 중인지, 파일 경로가 올바른지 확인하세요.');
      return null;
    }
  }

  // =====================================================================================
  // STEP 2: 데이터 처리 및 네트워크 생성
  // =====================================================================================

  const loadedData = await fetchData();
  if (!loadedData) return;

  const { fileSystemData, linksData, colorsData } = loadedData;

  const nodesArray = [];
  const nodeGroupInfo = {};

  fileSystemData.forEach(file => {
    const pathParts = file.path.replace(/^doc\//, '').split('/').filter(p => !p.endsWith('.md'));
    const fileName = file.path.split('/').pop();
    nodesArray.push({ id: fileName, label: fileName, content: file.content });
    nodeGroupInfo[fileName] = pathParts.filter(p => p !== fileName);
  });

  const edgesArray = linksData.trim().split('\n').map(line => {
    const trimmedLine = line.trim();
    if (!trimmedLine) return null;
    const getFileName = (path) => path.trim().split('/').pop();
    if (trimmedLine.includes('->')) {
      const [fromPath, toPath] = trimmedLine.split('->');
      return { from: getFileName(fromPath), to: getFileName(toPath), arrows: 'to' };
    } else if (trimmedLine.includes('--')) {
      const [fromPath, toPath] = trimmedLine.split('--');
      return { from: getFileName(fromPath), to: getFileName(toPath), arrows: '' };
    }
    return null;
  }).filter(Boolean);

  const nodes = new vis.DataSet(nodesArray);
  const edges = new vis.DataSet(edgesArray);

  // =====================================================================================
  // STEP 3: 네트워크 및 D3 설정 (물리 엔진 옵션 수정)
  // =====================================================================================

  const { groupFillColors, groupBorderColors } = colorsData;
  const container = document.getElementById('mynetwork');
  const data = { nodes: nodes, edges: edges };
  const options = {
    nodes: { shape: 'dot', size: 20, font: { size: 18 } },
    physics: {
      forceAtlas2Based: {
        gravitationalConstant: -100,
        centralGravity: 0.005,
        springLength: 230,
        springConstant: 0.08,
        avoidOverlap: 0.5
      },
      maxVelocity: 150,
      minVelocity: 0.75,
      solver: 'forceAtlas2Based',
      timestep: 0.35,
      stabilization: { iterations: 150 }
    }
  };

  const network = new vis.Network(container, data, options);
  const svg = d3.select("#hull-svg");
  const hullGroup = svg.append("g");

  // =====================================================================================
  // STEP 4: 부드러운 영역 그리기 (디자인 개선)
  // =====================================================================================

  function drawGroupHulls() {
    const nodePositions = network.getPositions();
    const groups = {};

    // 1. 노드 위치를 그룹별로 정리
    for (const nodeId in nodeGroupInfo) {
      const nodeGroups = nodeGroupInfo[nodeId];
      if (nodeGroups && nodeGroups.length > 0 && nodePositions[nodeId]) {
        const canvasPos = network.canvasToDOM(nodePositions[nodeId]);
        nodeGroups.forEach(groupName => {
          if (!groups[groupName]) groups[groupName] = [];
          groups[groupName].push([canvasPos.x, canvasPos.y]);
        });
      }
    }

    hullGroup.selectAll("path").remove();

    // 2. 그룹별로 부드러운 곡선 영역 그리기
    for (const groupName in groups) {
      const groupNodes = groups[groupName];
      if (groupNodes.length < 1) continue;

      // 노드가 1~2개일 때도 영역을 만들기 위해 주변에 가상 포인트 추가
      const padding = 40;
      let paddedNodes = [];
      if (groupNodes.length <= 2) {
        groupNodes.forEach(n => {
            paddedNodes.push([n[0] - padding, n[1] - padding]);
            paddedNodes.push([n[0] - padding, n[1] + padding]);
            paddedNodes.push([n[0] + padding, n[1] - padding]);
            paddedNodes.push([n[0] + padding, n[1] + padding]);
        });
      } else {
        paddedNodes = groupNodes;
      }

      // 3. d3.polygonHull로 기본 윤곽선을 잡고, d3.line과 curve로 부드럽게 그림
      const hull = d3.polygonHull(paddedNodes);
      if (hull) {
        const line = d3.line()
          .curve(d3.curveCatmullRom.alpha(0.7)) // 곡선 알고리즘 적용
          .x(d => d[0])
          .y(d => d[1]);

        hullGroup.append("path")
          .attr("d", line(hull) + "Z") // Z를 붙여 폐곡선으로 만듦
          .style("fill", groupFillColors[groupName] || 'rgba(128,128,128,0.2)')
          .style("stroke", groupBorderColors[groupName] || 'grey')
          .style("stroke-width", 3)
          .style("stroke-linejoin", "round");
      }
    }
  }

  network.on("afterDrawing", drawGroupHulls);

  // =====================================================================================
  // STEP 5: 팝업 로직
  // =====================================================================================

  const popup = document.getElementById('popup');
  const popupContent = document.getElementById('popup-content');
  const popupClose = document.getElementById('popup-close');

  network.on('click', function(params) {
    if (params.nodes.length > 0) {
      const node = nodes.get(params.nodes[0]);
      if (node.content) {
        popupContent.innerHTML = marked.parse(node.content);
        popup.style.display = 'block';
      }
    }
  });

  popupClose.onclick = () => { popup.style.display = 'none'; };
  window.onclick = (event) => { if (event.target == popup) popup.style.display = 'none'; };
});
