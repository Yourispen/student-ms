'use client';

import { signIn, signOut, useSession } from "next-auth/react";

export default function LoginPage() {
  const { data: session } = useSession();

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <h1 className="text-3xl font-bold mb-4">
        {session ? `Bienvenue, ${session.user?.name}` : "Connexion"}
      </h1>
      {session ? (
        <>
          <p className="text-gray-600">Vous êtes connecté.</p>
          <button
            onClick={() => signOut()}
            className="mt-4 bg-red-500 text-white px-4 py-2 rounded"
          >
            Se déconnecter
          </button>
        </>
      ) : (
        <button
          onClick={() => signIn("keycloak")}
          className="bg-blue-600 text-white px-4 py-2 rounded"
        >
          Se connecter avec Keycloak
        </button>
      )}
    </div>
  );
}
