import fs from 'node:fs';
import { createCanvas, DOMMatrix, ImageData, Path2D } from '@napi-rs/canvas';
Object.assign(globalThis, { DOMMatrix, ImageData, Path2D });
const { getDocument } = await import('pdfjs-dist/legacy/build/pdf.mjs');
const pdf = await getDocument({ data: new Uint8Array(fs.readFileSync('Wealth-e_Maquettes_Mobile_UIUX.pdf')), useSystemFonts: true }).promise;
fs.mkdirSync('design', { recursive: true });
let text = '';
for (let i = 1; i <= pdf.numPages; i++) {
 const page = await pdf.getPage(i);
 text += `\n--- PAGE ${i} ---\n` + (await page.getTextContent()).items.map(x => x.str).join('\n');
 const viewport = page.getViewport({ scale: 1.2 });
 const canvas = createCanvas(viewport.width, viewport.height);
 await page.render({ canvasContext: canvas.getContext('2d'), viewport }).promise;
 fs.writeFileSync(`design/page-${i}.png`, canvas.toBuffer('image/png'));
}
fs.writeFileSync('design/pdf-content.txt', text);
console.log(text);
