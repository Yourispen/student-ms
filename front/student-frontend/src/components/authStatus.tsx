"use client";

import { useSession, signIn, signOut } from "next-auth/react";
import { useEffect } from "react";
import { FiLogIn, FiLogOut } from "react-icons/fi"; // Importation des icônes

async function keycloakSessionLogOut() {
  try {
    await fetch(`/api/auth/logout`, { method: "GET" });
  } catch (err) {
    console.error(err);
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export default function AuthStatus({ onSessionChange }: { onSessionChange?: (session: any) => void }) {
  const { data: session, status } = useSession();

  useEffect(() => {
    if (status !== "loading" && session) {
      if (onSessionChange) {
        onSessionChange(session);
      }
      //console.log("session authsatus: ",session)
      if (session.error === "RefreshAccessTokenError") {
        signOut({ callbackUrl: "/" });
      }
    }
  }, [status, onSessionChange]);

  if (status === "loading") {
    return <div className="flex justify-center my-3 text-gray-600">Chargement...</div>;
  }
  //console.log("session nav:",session)


  return (
    <div className="flex items-center space-x-4 p-4 bg-gray-800 text-white rounded-lg shadow-md">
      {session ? (
        <>
          <div className="flex items-center space-x-2">
            <span className="text-lg font-semibold">Connecté en tant que</span>
            <span className="text-yellow-300 font-medium">{session.user?.email}</span>
          </div>
          <button
            className="flex items-center space-x-2 bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg shadow transition duration-300"
            onClick={() => {
              keycloakSessionLogOut().then(() => signOut({ callbackUrl: "/" }));
            }}
          >
            <FiLogOut className="text-xl" />
            <span>Se Déconnecter</span>
          </button>
        </>
      ) : (
        <button
          className="flex items-center space-x-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg shadow transition duration-300"
          onClick={() => signIn("keycloak")}
        >
          <FiLogIn className="text-xl" />
          <span>Se Connecter</span>
        </button>
      )}
    </div>
  );
}



