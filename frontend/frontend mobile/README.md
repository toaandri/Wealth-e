# Wealth-e · Mobile

Application React Native / TypeScript avec Expo Go, réalisée uniquement dans `frontend mobile` à partir des 34 pages de `Wealth-e_Maquettes_Mobile_UIUX.pdf`.

## Lancer

```powershell
cd 'C:\Wealth-e\frontend\frontend mobile'
npm install
npm start
```

Scanner le QR code avec une version d’Expo Go compatible avec le SDK indiqué dans `package.json`. Le téléphone et le PC doivent partager le même réseau. Pour un aperçu navigateur : `npm run web`.

## Configuration API

Copier `.env.example` vers `.env`, puis définir `EXPO_PUBLIC_API_BASE_URL` selon l’environnement :

- émulateur Android : `http://10.0.2.2:8080` ;
- simulateur iOS ou navigateur sur le PC : `http://localhost:8080` ;
- téléphone physique : `http://<adresse-IP-LAN-du-PC>:8080`.

Le backend REST doit être démarré et accessible depuis l’appareil. Les variables `EXPO_PUBLIC_` sont embarquées dans l’application ; n’y placer aucun secret serveur.

## Parcours

Accueil → inscription ou connexion au backend → onboarding → tableau de bord. Les cinq onglets donnent accès aux comptes, actifs, analyses et préférences. L’icône grille en haut à droite liste les **34 pages dans l’ordre du PDF**. Les boutons Précédent / Suivant permettent de parcourir toute la maquette.

Le fond, les cartes, les couleurs, les tableaux, les graphiques illustratifs, les badges et la navigation reprennent la maquette, avec des champs et zones tactiles adaptés aux téléphones. Les rendus du PDF sont reproductibles avec `npm run design:extract` et enregistrés dans `design/`.

## Configuration et Git

Le fichier `.env` reste local et est ignoré par Git. `.env.example` est le modèle à versionner. L’adresse du backend est configurable avec `EXPO_PUBLIC_API_BASE_URL`. Les variables `EXPO_PUBLIC_` sont publiques dans l’application : ne pas y placer de secrets serveur.

Les dépendances, caches Expo, builds, journaux, captures générées et fichiers de signature sont exclus par `.gitignore`. Conserver `package-lock.json`, les sources, la maquette PDF et `design/pdf-content.txt` (utilisé par les tests) dans Git.

## Organisation du code

- `src/screens/` : 34 fichiers d’écran, un pour chaque page du PDF.
- `src/navigation/` : ordre des pages, groupes et routeur des 34 composants.
- `src/components/` : cartes, boutons, champs, graphiques et vues réutilisées par les écrans de même famille.
- `src/state/WealthContext.tsx` : état partagé, sauvegarde locale et synchronisation des comptes / transactions.
- `src/api/client.ts` : authentification et appels REST vers le backend.
- `src/theme/` : palette et styles communs.
- `src/model.ts` : données, calculs, recommandations et lecture CSV.
- `App.tsx` : structure de l’application, menu et barre des onglets.

## Fonctionnalités

- Synthèse recalculée des actifs, dettes et patrimoine net.
- Inscription et connexion au backend REST ; session et cache local conservés avec AsyncStorage.
- Comptes et transactions synchronisés avec le backend après connexion ; création de comptes, revenus et dépenses persistés côté serveur.
- Biens, prêts, budgets et alertes locaux en mode démonstration.
- Transferts entre comptes et achats / ventes simulés en mode déconnecté ; ces opérations sont signalées comme non prises en charge lorsque le compte serveur est actif.
- Recherche et filtres, détails sélectionnés, suivi des budgets du mois courant.
- Import CSV par collage avec validation et prévisualisation avant confirmation. Colonnes : `date;libelle;montant;categorie`. Format simple sans champs contenant le séparateur.
- Export CSV dans le navigateur ; partage du contenu sur mobile. Les rapports sont des CSV / contenus partagés, pas des PDF générés.
- Simulations avec capitalisation mensuelle, montant initial, versements, durée et taux hypothétique.
- Recommandations locales fondées sur la liquidité, la concentration et les budgets.
- Assistant simulé en lecture seule, explications, scénario textuel avec confirmation, glossaire éducatif local.
- Profil, devise d’affichage, plans fictifs et sauvegarde locale AsyncStorage.
- Paramètres organisés en informations personnelles, préférences, abonnement et suivi, données et connexions, assistant et aide. Profil prérempli, validation, annulation et état du bouton de sauvegarde selon les modifications.
- Mode de consultation hors-ligne en lecture seule.

## Limites explicites

Seuls l’inscription, la connexion, les comptes et les transactions sont actuellement reliés au backend ; les autres modules restent locaux ou illustratifs. Le backend émet actuellement un jeton de démonstration et ne protège pas encore les routes financières par utilisateur. Le connecteur bancaire et les plans sont fictifs. L’assistant utilise des règles locales, sans modèle d’IA ni moteur RAG distant. Les graphiques historiques et le +4,2 % sont illustratifs. La devise change uniquement le libellé, sans conversion. Les alertes s’affichent dans l’application, sans notification système. Les rendements et recommandations ne sont pas des conseils financiers.

## Vérifications

```powershell
npm run typecheck
npm test
npm run export:web
```

Le modèle de données, l’ordre des pages, l’import CSV, les recommandations et les simulations sont couverts par les tests.
