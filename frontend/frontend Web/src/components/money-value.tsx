type MoneyValueProps = {
  amount: number | null;
  currency?: string;
  className?: string;
};

const formatter = new Intl.NumberFormat("fr-FR", {
  maximumFractionDigits: 0
});

export function MoneyValue({
  amount,
  currency = "MGA",
  className
}: MoneyValueProps) {
  if (amount === null) {
    return (
      <span className={className} aria-label="Valeur indisponible">
        —
      </span>
    );
  }

  const suffix = currency === "MGA" ? "Ar" : currency;

  return (
    <span className={className}>
      {formatter.format(amount)} {suffix}
    </span>
  );
}
