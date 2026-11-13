export type Account = { id: string; name: string; balance: number; kind: string };
export type Transaction = { id: string; name: string; amount: number; category: string; date: string; account: string };
export type Position = { id: string; name: string; value: number; performance: number };
export type Store = {
 profile: { name: string; email: string; currency: string }; accounts: Account[];
 transactions: Transaction[]; positions: Position[];
 properties: { id: string; name: string; value: number }[];
 loans: { id: string; name: string; balance: number; year: number }[];
 budgets: { id: string; name: string; limit: number }[];
 alerts: { id: string; name: string; threshold: number; enabled: boolean }[];
 plan: string; connected: boolean; offline: boolean; updatedAt: string;
};
export const initialStore: Store = {
 profile: { name: 'Alex', email: 'alex@exemple.com', currency: 'Ar' },
 accounts: [{ id: 'current', name: 'Compte courant', balance: 5000000, kind: 'Courant' }, { id: 'saving', name: 'Épargne', balance: 2000000, kind: 'Épargne' }],
 transactions: [
  { id: 't1', name: 'Salaire', amount: 2000000, category: 'Revenu', date: '2026-11-01', account: 'current' },
  { id: 't2', name: 'Courses', amount: -180000, category: 'Alimentation', date: '2026-11-04', account: 'current' },
  { id: 't3', name: 'Transfert interne', amount: 100000, category: 'Transfert', date: '2026-11-05', account: 'saving' },
  { id: 't4', name: 'Loyer', amount: -500000, category: 'Logement', date: '2026-11-06', account: 'current' },
 ],
 positions: [{ id: 'etf', name: 'ETF Monde', value: 1500000, performance: 4.2 }, { id: 'stock', name: 'Action A', value: 900000, performance: -1.3 }, { id: 'bond', name: 'Obligation démo', value: 600000, performance: 2.1 }],
 properties: [{ id: 'house', name: 'Maison', value: 80000000 }],
 loans: [{ id: 'mortgage', name: 'Prêt immobilier', balance: 8000000, year: 2032 }, { id: 'personal', name: 'Prêt personnel', balance: 2000000, year: 2028 }],
 budgets: [{ id: 'food', name: 'Alimentation', limit: 300000 }, { id: 'housing', name: 'Logement', limit: 600000 }, { id: 'leisure', name: 'Loisirs', limit: 200000 }],
 alerts: [{ id: 'a1', name: 'Solde du compte courant', threshold: 1000000, enabled: true }],
 plan: 'Free', connected: false, offline: false, updatedAt: '2026-11-06T12:00:00.000Z',
};
export function totals(s: Store) {
 const cash = s.accounts.reduce((a, b) => a + b.balance, 0);
 const investments = s.positions.reduce((a, b) => a + b.value, 0);
 const property = s.properties.reduce((a, b) => a + b.value, 0);
 const debt = s.loans.reduce((a, b) => a + b.balance, 0);
 return { cash, investments, property, debt, assets: cash + investments + property, net: cash + investments + property - debt };
}
export function project(initial: number, monthly: number, months: number, rate: number) {
 let value = initial;
 for (let i = 0; i < months; i++) value = value * (1 + rate / 1200) + monthly;
 return Math.round(value);
}
export function recommend(s: Store) {
 const expenses = -s.transactions.filter(t => t.amount < 0).reduce((a, b) => a + b.amount, 0);
 const t = totals(s);
 return [
  { title: 'Votre réserve de sécurité', body: `${Math.round(t.cash / Math.max(expenses, 1))} mois de dépenses couverts par vos liquidités. Comparez avec un objectif de démonstration de 3 à 6 mois.`, page: 24 },
  { title: 'Votre répartition', body: `L’immobilier représente ${Math.round(t.property / Math.max(t.assets, 1) * 100)} % de vos actifs. Explorez la répartition et simulez d’autres allocations.`, page: 22 },
  ...s.budgets.filter(b => -s.transactions.filter(t => t.category === b.name && t.amount < 0).reduce((a, t) => a + t.amount, 0) >= b.limit * .8).map(b => ({ title: `Budget ${b.name}`, body: 'Plus de 80 % du budget est utilisé. Consultez les dépenses de cette catégorie.', page: 12 })),
 ];
}
export function parseCSV(raw: string, account: string): Transaction[] {
 const lines = raw.trim().split(/\r?\n/);
 if (lines.length < 2) throw new Error('Ajoutez un en-tête et au moins une opération.');
 const separator = lines[0].includes(';') ? ';' : ',';
 if (lines[0].toLowerCase().replace(/\s/g, '') !== ['date', 'libelle', 'montant', 'categorie'].join(separator)) throw new Error('En-tête attendu : date;libelle;montant;categorie');
 return lines.slice(1).map((line, i) => {
  const [date, name, amount, category] = line.split(separator).map(v => v.trim());
  const n = Number(amount?.replace(',', '.'));
  if (!/^\d{4}-\d{2}-\d{2}$/.test(date) || !Number.isFinite(Date.parse(date)) || new Date(date).toISOString().slice(0, 10) !== date || !name || !amount || !Number.isFinite(n) || !category) throw new Error(`Ligne ${i + 2} invalide.`);
  return { id: `csv-${Date.now()}-${i}`, date, name, amount: n, category, account };
 });
}
