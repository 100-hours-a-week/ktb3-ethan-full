import os
import re

# 1) 기존 내용뒤에 이어쓰는게 아니라 전부 지우고 새로 쓰는방식으로 변경
def overwrite_readme(readme_path, content_to_insert):
    """파일의 전체 내용을 새로 작성합니다."""
    with open(readme_path, 'w', encoding='utf-8') as f:
        f.write(content_to_insert)
    print(f"✅ {os.path.basename(readme_path)} has been successfully updated.")


def generate_keyword_table():
    keyword_dir = 'keyword'
    
    try:
        # 2) 기록을 하기전에 텍스트파일 제목을 기준으로 정렬
        txt_files = sorted(
            [f for f in os.listdir(keyword_dir) if re.match(r'w\d+-d\d+\.txt', f)]
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

    # 생성된 전체 내용을 README에 삽입합니다.
    content_to_insert = "\n\n".join(all_markdown_blocks)
    overwrite_readme(os.path.join(keyword_dir, 'README.md'), content_to_insert)


if __name__ == "__main__":
    generate_keyword_table()
