import { authOptions } from "../[...nextauth]/route";
import { getServerSession } from "next-auth";
import { getIdToken } from "@/utils/sessionTokenAccessor";
import { NextResponse } from "next/server";

export async function GET() {
  const session = await getServerSession(authOptions);

  if (session) {
    try {
      const idToken = await getIdToken();

      // Construction de l'URL pour la déconnexion sur Keycloak
      const url = `${process.env.END_SESSION_URL}?id_token_hint=${idToken}&post_logout_redirect_uri=${encodeURIComponent(
        process.env.NEXTAUTH_URL!
      )}`;

      // Envoi de la requête GET
      const resp = await fetch(url, { method: "GET" });

      // Vérification de la réponse
      if (!resp.ok) {
        console.error(`Keycloak logout failed with status: ${resp.status}`);
        return NextResponse.json(
          { error: "Logout failed on Keycloak side" },
          { status: 500 }
        );
      }
    } catch (err) {
      console.error("Erreur lors de la déconnexion :", err);
      return NextResponse.json(
        { error: "Erreur lors de la déconnexion" },
        { status: 500 }
      );
    }
  }

  return NextResponse.json({ message: "Déconnexion réussie" }, { status: 200 });
}