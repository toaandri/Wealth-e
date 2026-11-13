const http = require('node:http');
const fs = require('node:fs');
const path = require('node:path');
const root = path.resolve('dist');
const types = { '.html': 'text/html', '.js': 'application/javascript', '.css': 'text/css', '.ttf': 'font/ttf', '.png': 'image/png', '.json': 'application/json' };
http.createServer((req, res) => {
 const file = path.resolve(root, '.' + decodeURIComponent(req.url.split('?')[0] === '/' ? '/index.html' : req.url.split('?')[0]));
 if (!file.startsWith(root + path.sep) || !fs.existsSync(file)) { res.writeHead(404); res.end(); return; }
 res.setHeader('Content-Type', types[path.extname(file)] || 'application/octet-stream');
 fs.createReadStream(file).pipe(res);
}).listen(8087, '127.0.0.1', () => console.log('Preview http://127.0.0.1:8087'));
