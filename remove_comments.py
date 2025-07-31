import sys, os, re

extensions = {
    '.js', '.ts', '.tsx', '.jsx', '.java', '.kt', '.kts', '.css', '.scss',
    '.c', '.h', '.cpp', '.hpp', '.go', '.py', '.sh', '.yml', '.yaml',
    '.gradle', '.groovy', '.html', '.xml', '.json', '.md'
}

single_comment_patterns = [
    re.compile(r'^\s*//.*$', re.MULTILINE),
    re.compile(r'^\s*#.*$', re.MULTILINE)
]

block_comment_patterns = [
    re.compile(r'/\*.*?\*/', re.DOTALL),
    re.compile(r'', re.DOTALL)
]

for root, _, files in os.walk('.'):
    for name in files:
        ext = os.path.splitext(name)[1]
        if ext.lower() not in extensions:
            continue
        path = os.path.join(root, name)
        try:
            with open(path, 'r', encoding='utf-8') as f:
                data = f.read()
        except Exception:
            continue
        for pattern in block_comment_patterns:
            data = pattern.sub('', data)
        for pattern in single_comment_patterns:
            data = pattern.sub('', data)
        with open(path, 'w', encoding='utf-8') as f:
            f.write(data)
