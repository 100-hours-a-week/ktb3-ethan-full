import os
import re

def generate_keyword_table():
    """keyword 폴더의 txt 파일을 읽어 README에 삽입할 마크다운 테이블을 생성합니다."""
    keyword_dir = 'keyword'
    readme_path = os.path.join(keyword_dir, 'README.md')
    
    # 1. keyword 폴더에서 'w<숫자>-d<숫자>.txt' 형식의 파일 목록을 찾습니다.
    try:
        txt_files = sorted(
            [f for f in os.listdir(keyword_dir) if re.match(r'w\d+-d\d+\.txt', f)],
            key=lambda x: (int(re.search(r'w(\d+)', x).group(1)), int(re.search(r'd(\d+)', x).group(1)))
        )
        # 디버깅을 위해 찾은 파일 목록을 출력합니다.
        print(f"✅ Found keyword files: {txt_files}")
        if not txt_files:
            print("⚠️ No keyword files found. Nothing to update.")
            return

    except FileNotFoundError:
        print(f"❌ Error: '{keyword_dir}' directory not found.")
        return

    all_markdown_blocks = []

    # 2. 각 파일을 순회하며 마크다운 블록을 생성합니다.
    for filename in txt_files:
        match = re.match(r'w(\d+)-d(\d+)\.txt', filename)
        if not match:
            continue
            
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
                if not line:  # 빈 줄은 건너뜁니다.
                    continue

                # 👇 콜론(:)이 없는 경우도 처리하도록 수정된 부분
                if ':' in line:
                    keyword, definition = line.split(':', 1)
                    keyword = keyword.strip().replace('|', '\|')
                    definition = definition.strip().replace('|', '\|')
                else:
                    keyword = line.strip().replace('|', '\|')
                    definition = ''  # 콜론이 없으면 '정리' 부분을 비워둡니다.
                
                markdown_lines.append(f"| {keyword} | {definition} |")

        markdown_lines.append("\n</details>\n")
        all_markdown_blocks.append("\n".join(markdown_lines))

    # 3. README.md 파일을 업데이트합니다.
    try:
        with open(readme_path, 'r', encoding='utf-8') as f:
            readme_content = f.read()

        start_marker = ''
        end_marker = ''
        
        # 주석 사이의 내용을 새로 생성된 마크다운 블록으로 '완전히' 교체합니다.
        content_to_insert = "\n".join(all_markdown_blocks)
        new_readme_content = re.sub(
            f"({re.escape(start_marker)})(.*?)({re.escape(end_marker)})",
            f"\\1\n{content_to_insert}\n\\3",
            readme_content,
            flags=re.DOTALL
        )

        with open(readme_path, 'w', encoding='utf-8') as f:
            f.write(new_readme_content)
        print("✅ README.md has been successfully updated.")

    except FileNotFoundError:
        print(f"❌ Error: '{readme_path}' not found. Please create it first.")
    except Exception as e:
        print(f"❌ An error occurred: {e}")


if __name__ == "__main__":
    generate_keyword_table()
