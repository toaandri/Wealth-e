const { test } = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const { stripTypeScriptTypes } = require('node:module');
function load(path, names) {
 const code = stripTypeScriptTypes(fs.readFileSync(path, 'utf8')).replace(/^export /gm, '') + '\nmodule.exports = {' + names.join(',') + '};';
 const module = { exports: {} };
 new Function('exports', 'require', 'module', code)(module.exports, require, module);
 return module.exports;
}
const { initialStore, parseCSV, project, recommend, totals } = load('src/model.ts', ['initialStore', 'parseCSV', 'project', 'recommend', 'totals']);
const { screens, groups } = load('src/navigation/routes.ts', ['screens', 'groups']);
test('34 pages dans l’ordre du PDF sans doublon', () => {
 assert.equal(screens.length, 34);
 assert.deepEqual(groups.flatMap(g => g.pages), Array.from({ length: 34 }, (_, i) => i));
 assert.equal(fs.readdirSync('src/screens').filter(f => f.endsWith('Screen.tsx')).length, 34);
 const pdfTitles = [...fs.readFileSync('design/pdf-content.txt', 'utf8').matchAll(/WEALTH-E \| MOBILE \| \d+\/34 \| ([^\r\n]+)/g)].map(m => m[1].trim());
 assert.deepEqual(screens.map(s => s[0]), pdfTitles);
});
test('synthèse conforme aux montants du PDF', () => {
 assert.deepEqual(totals(initialStore), { cash: 7000000, investments: 3000000, property: 80000000, debt: 10000000, assets: 90000000, net: 80000000 });
});
test('un transfert interne conserve le patrimoine', () => {
 const changed = { ...initialStore, accounts: initialStore.accounts.map((a, i) => ({ ...a, balance: a.balance + (i === 0 ? -100000 : 100000) })) };
 assert.equal(totals(changed).net, totals(initialStore).net);
});
test('projection avec capitalisation mensuelle et versements', () => {
 assert.equal(project(100000, 50000, 12, 0), 700000);
 assert.equal(project(100000, 0, 12, 12), 112683);
});
test('import CSV : montants signés et erreurs', () => {
 const [row] = parseCSV('date;libelle;montant;categorie\n2026-11-07;Courses;-45000;Alimentation', 'current');
 assert.equal(row.amount, -45000);
 assert.equal(row.account, 'current');
 assert.equal(parseCSV('date,libelle,montant,categorie\n2026-11-07,Salaire,2000000,Revenu', 'current')[0].amount, 2000000);
 assert.throws(() => parseCSV('date;libelle;montant;categorie\n2026-11-07;Courses;abc;Alimentation', 'current'));
 assert.throws(() => parseCSV('invalide\nune ligne', 'current'));
 assert.throws(() => parseCSV('date;libelle;montant;categorie\n2026-02-31;Courses;100;Alimentation', 'current'));
});
test('recommandations fondées sur les données et pages valides', () => {
 const recommendations = recommend(initialStore);
 assert.ok(recommendations.some(r => r.title.includes('Logement')));
 assert.ok(recommendations.every(r => r.page >= 0 && r.page < 34));
 assert.match(recommendations[1].body, /89 %/);
});
