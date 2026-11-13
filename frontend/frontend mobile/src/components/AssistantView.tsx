import React from 'react';
import { Text, View } from 'react-native';
import { styles } from '../theme/styles';
import { Button, Card, Field } from '../components';

import { project, recommend } from '../model';
import { useWealth } from '../state/WealthContext';

export function AssistantView() {
 const { store, page, error, result, setResult, question, setQuestion, messages, setMessages, natural, setNatural, t, fmt, run, link } = useWealth();

  const send = () => run(() => {
   if (!question.trim()) throw new Error('Posez votre question avant de l’envoyer.');
   let answer = `Votre patrimoine net est de ${fmt(t.net)} : ${fmt(t.assets)} d’actifs moins ${fmt(t.debt)} de dettes. Les +4,2 % affichés sont une illustration de la maquette ; aucun historique réel ne permet d’en expliquer la variation.`;
   if (page === 30) {
    const values = question.match(/\d+(?:[.,]\d+)?/g)?.map(v => Number(v.replace(',', '.'))) || [];
    if (values.length < 2) throw new Error('Exemple : « Épargne 100000 initiale, 50000 par mois, pendant 24 mois, taux 3 % ». Indiquez les quatre valeurs dans cet ordre.');
    const params = { initial: values[0], monthly: values[1], months: values[2] ?? 12, rate: values[3] ?? 0 };
    if (params.months < 1 || params.months > 600 || !Number.isInteger(params.months) || params.rate > 100) throw new Error('La durée doit être comprise entre 1 et 600 mois et le taux entre 0 et 100 %.');
    setNatural(params); answer = `Paramètres extraits à confirmer : ${fmt(params.initial)} au départ, ${fmt(params.monthly)} par mois, ${params.months} mois, ${params.rate} % annuels. Aucun calcul exécuté avant confirmation.`;
   } else if (page === 31) answer = 'La diversification consiste à répartir ses actifs entre plusieurs catégories pour limiter la concentration. Elle ne garantit pas l’absence de perte. Références locales : fiche « Diversification » et fiche « Risque et rendement » (glossaire de démonstration, disponible ci-dessous). Aucun moteur RAG ni service distant connecté.';
   else if (/recommand|conseil|budget|répart/i.test(question)) answer = recommend(store).map(r => `${r.title} : ${r.body}`).join('\n\n');
   setMessages(m => [...m, { question, answer }]); setQuestion('');
  });
  return <><Card title="Assistant Wealth-e"><Text style={styles.body}>Lecture seule · réponses locales à partir des données démo</Text><View style={styles.notice}><Text style={styles.noticeText}>Question : Pourquoi mon patrimoine a-t-il évolué ce mois-ci ?</Text></View><Text style={styles.body}>Le +4,2 % est une valeur illustrative. Les calculs de synthèse utilisent vos données locales.</Text>{messages.map((m, i) => <View key={i} style={{ marginTop: 16 }}><Text style={styles.label}>{m.question}</Text><Text style={styles.body}>{m.answer}</Text></View>)}<Field label={page === 30 ? 'Votre scénario (initial, mensuel, mois, taux)' : 'Votre question'} value={question} onChangeText={setQuestion} multiline /><View style={styles.wrap}><Button title="Envoyer" onPress={send} /></View>{!!error && <Text style={styles.error}>{error}</Text>}
   {page === 30 && natural && <View style={styles.notice}><Text style={styles.noticeText}>Vérifiez les paramètres extraits avant de simuler.</Text><View style={styles.wrap}><Button title="Confirmer et simuler" onPress={() => { const p = natural; setResult(`Capital simulé : ${fmt(project(p.initial, p.monthly, p.months, p.rate))} · hors frais et fiscalité.`); setNatural(null); }} /><Button title="Annuler" secondary onPress={() => setNatural(null)} /></View></View>}{!!result && <Text style={styles.label}>{result}</Text>}
  </Card><View style={styles.summary}><Card title="Garde-fous IA"><Text style={styles.body}>Aucune écriture financière. Assistant simulé, sans API d’IA. Les recommandations sont des règles de démonstration.</Text></Card></View>
  {page === 31 && <Card title="Glossaire local"><Text style={styles.label}>Diversification</Text><Text style={styles.body}>Répartir les actifs entre plusieurs catégories, secteurs ou zones géographiques.</Text><Text style={styles.label}>Risque et rendement</Text><Text style={styles.body}>Un rendement potentiel plus élevé s’accompagne généralement d’un risque de perte plus élevé.</Text></Card>}
  <Card title="Explorer l’assistant">{link('Explication IA', 29)}{link('Scénario en langage naturel', 30)}{link('Recherche éducative RAG', 31)}</Card></>;
 }

