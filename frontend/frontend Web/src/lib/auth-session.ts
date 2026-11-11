export type AuthUser = {
  id: number;
  email: string;
  fullName: string;
  currency: string;
};

export type AuthSession = {
  token: string;
  user: AuthUser;
};

const SESSION_KEY = "wealth-e-auth-session";

export function getAuthSession(): AuthSession | null {
  const serializedSession = sessionStorage.getItem(SESSION_KEY);
  if (!serializedSession) return null;

  try {
    const session = JSON.parse(serializedSession) as AuthSession;
    return session?.token && session.user?.id ? session : null;
  } catch {
    sessionStorage.removeItem(SESSION_KEY);
    return null;
  }
}

export function saveAuthSession(session: AuthSession): void {
  sessionStorage.setItem(SESSION_KEY, JSON.stringify(session));
}

export function clearAuthSession(): void {
  sessionStorage.removeItem(SESSION_KEY);
}
