import { useAuth } from 'oidc-react';


export default function useCustomAuth() {
  const auth = useAuth();


  return {
    isAuthenticated: !!auth?.userData,
    user: auth?.userData?.profile,
    signIn: auth.signIn,
    signOut: auth.signOut,
  };
}
