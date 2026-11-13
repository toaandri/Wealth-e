export const money = (n: number, currency = 'Ar') => `${Math.round(n).toLocaleString('fr-FR')} ${currency}`;
export const id = () => `${Date.now()}-${Math.random().toString(36).slice(2, 7)}`;
export const today = () => new Date().toISOString().slice(0, 10);
