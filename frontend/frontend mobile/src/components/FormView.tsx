import React from 'react';
import { Text } from 'react-native';
import { styles } from '../theme/styles';
import { Card } from '../components';
import { screens } from '../navigation/routes';

import { useWealth } from '../state/WealthContext';

export function FormView() {
 const { store, page, draft, amount, field, pick, accountPicker, link, info, actions, submitForm } = useWealth();

  const auth = [1, 2, 14].includes(page);
  return <><Card title={screens[page][0]}><Text style={styles.body}>{auth ? 'Formulaire de démonstration — aucun compte distant ni mot de passe enregistré.' : 'Renseignez les informations ci-dessous.'}</Text>
   {auth ? <>{page === 1 && field('name', 'Nom')}{field('email', 'Adresse e-mail')}{field('password', 'Mot de passe', false, true)}{page === 1 && field('confirm', 'Confirmation', false, true)}</> : page === 3 ? <>{field('name', 'Nom / libellé')}<Text style={styles.label}>Devise d’affichage</Text>{pick('currency', ['Ar', 'EUR', 'USD'])}<Text style={styles.tiny}>Changer le libellé de devise ne convertit pas les montants démo.</Text></> : page === 24 ? <>{field('initial', 'Épargne initiale', true)}{field('monthly', 'Versement mensuel', true)}{field('months', 'Durée (mois)', true)}{field('rate', 'Hypothèse de taux annuel (%)', true)}</> : <>
    {page === 10 && <>{field('name', 'Nom / libellé')}<Text style={styles.label}>Type d’opération</Text>{pick('type', ['Dépense', 'Revenu'])}</>}
    {page === 17 && <><Text style={styles.label}>Opération</Text>{pick('trade', ['Acheter', 'Vendre'])}<Text style={styles.label}>Position</Text>{pick('position', store.positions.map(p => p.name))}</>}
    {field('amount', 'Montant (' + store.profile.currency + ')', true)}{accountPicker('from')}{page === 11 && accountPicker('to')}
    {page !== 17 && <>{field('date', 'Date (AAAA-MM-JJ)')}<Text style={styles.tiny}>Date du jour utilisée si le champ est vide.</Text></>}
    {page === 10 && <><Text style={styles.label}>Catégorie / type</Text>{pick('category', draft.type === 'Revenu' ? ['Revenu', 'Autre'] : ['Alimentation', 'Logement', 'Loisirs', 'Transport', 'Autre'])}</>}
   </>}
   {actions(submitForm, page === 1 ? 'Créer mon compte démo' : page === 2 || page === 14 ? 'Se connecter' : page === 24 ? 'Simuler' : 'Valider')}
  </Card>{info}{page === 11 && link('Consulter les comptes', 7)}</>;
 }

