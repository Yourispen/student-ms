'use client';


import { AuthProvider, User } from 'oidc-react';


const oidcConfig = {
  authority: 'http://localhost:8080/realms/student_ms',
  clientId: 'account',
  redirectUri: 'http://localhost:3000/',
  responseType: 'code',
  scope: 'openid profile email',
  onSignIn: async (userData: User|null) => {
    if (userData) {
        console.log('Utilisateur connecté:', userData);
    }
  },
};


export function KeycloakAuthProvider({ children }: { children: React.ReactNode }) {
  return <AuthProvider {...oidcConfig}>{children}</AuthProvider>;
}
export { AuthProvider };

