type ComingSoonPageProps = { title: string };

export function ComingSoonPage({ title }: ComingSoonPageProps) {
  return (
    <section className="page-placeholder">
      <h1>{title}</h1>
      <p>Cet écran sera développé dans un prochain lot.</p>
    </section>
  );
}
