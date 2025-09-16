import os
import re

def update_readme_safely(readme_path, content_to_insert):
    """파일을 한 줄씩 분석하여 안정적으로 내용을 교체합니다."""
    start_marker = ''
    end_marker = ''

    try:
        with open(readme_path, 'r', encoding='utf-8') as f:
            lines = f.readlines()
    except FileNotFoundError:
        print(f"❌ Error: '{readme_path}' not found. Please create it first.")
        return

    # 시작 마커와 끝 마커의 위치를 찾습니다.
    try:
        start_index = next(i for i, line in enumerate(lines) if start_marker in line)
        end_index = next(i for i, line in enumerate(lines) if end_marker in line)
    except StopIteration:
        print(f"❌ Error: Markers '{start_marker}' or '{end_marker}' not found in README.")
        return
        
    # 마커 사이의 기존 내용을 삭제하고 새로운 내용으로 교체합니다.
    new_content_lines = content_to_insert.splitlines(True) # 줄바꿈 유지
    
    # 새로운 README 내용을 조립합니다.
    new_readme_lines = (
        lines[:start_index + 1] + 
        ['\n'] +
        new_content_lines + 
        ['\n'] +
        lines[end_index:]
    )

    with open(readme_path, 'w', encoding='utf-8') as f:
        f.writelines(new_readme_lines)
    print("✅ README.md has been successfully and safely updated.")


def generate_keyword_table():
    keyword_dir = 'keyword'
    
    try:
        txt_files = sorted(
            [f for f in os.listdir(keyword_dir) if re.match(r'w\d+-d\d+\.txt', f)],
            key=lambda x: (int(re.search(r'w(\d+)', x).group(1)), int(re.search(r'd(\d+)', x).group(1)))
        )
        print(f"✅ Found keyword files: {txt_files}")
    except FileNotFoundError:
        print(f"❌ Error: '{keyword_dir}' directory not found.")
        return

    all_markdown_blocks = []
    for filename in txt_files:
        match = re.match(r'w(\d+)-d(\d+)\.txt', filename)
        week, day = match.groups()
        
        markdown_lines = [
            f"<details>",
            f"<summary><strong>{week}주차 {day}일차 수업 키워드</strong></summary>\n",
            "| 키워드 | 정리 |",
            "|:---|:---|"
        ]
        
        file_path = os.path.join(keyword_dir, filename)
        with open(file_path, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                if not line: continue

                if ':' in line:
                    keyword, definition = line.split(':', 1)
                    keyword = keyword.strip().replace('|', '\|')
                    definition = definition.strip().replace('|', '\|')
                else:
                    keyword = line.strip().replace('|', '\|')
                    definition = ''
                
                markdown_lines.append(f"| {keyword} | {definition} |")

        markdown_lines.append("\n</details>")
        all_markdown_blocks.append("\n".join(markdown_lines))

    # 생성된 전체 내용을 안전하게 README에 삽입합니다.
    content_to_insert = "\n\n".join(all_markdown_blocks)
    update_readme_safely(os.path.join(keyword_dir, 'README.md'), content_to_insert)


if __name__ == "__main__":
    generate_keyword_table()
