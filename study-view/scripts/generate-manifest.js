
const fs = require('fs');
const path = require('path');

const docDir = path.join(__dirname, '../doc');
const manifestPath = path.join(__dirname, '../file-manifest.json');

// doc 폴더를 재귀적으로 탐색하여 모든 .md 파일의 경로를 찾습니다.
function findMdFiles(dir, fileList = []) {
  const files = fs.readdirSync(dir);

  files.forEach(file => {
    const filePath = path.join(dir, file);
    const stat = fs.statSync(filePath);

    if (stat.isDirectory()) {
      findMdFiles(filePath, fileList);
    } else if (path.extname(file) === '.md') {
      // study-view 폴더 기준의 상대 경로로 변환합니다.
      const relativePath = path.relative(path.join(__dirname, '..'), filePath).replace(/\\/g, '/');
      fileList.push(relativePath);
    }
  });

  return fileList;
}

try {
  const mdFiles = findMdFiles(docDir);
  fs.writeFileSync(manifestPath, JSON.stringify(mdFiles, null, 2));
  console.log(`Successfully generated file-manifest.json with ${mdFiles.length} files.`);
} catch (error) {
  console.error('Error generating file manifest:', error);
  process.exit(1);
}
