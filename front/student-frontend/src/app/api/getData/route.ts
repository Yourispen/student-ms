import { NextResponse } from "next/server";
import { getAccessToken } from "@/utils/sessionTokenAccessor";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";

export async function POST(req: Request) {
  // Vérifie la session utilisateur
  const session = await getServerSession(authOptions);

  if (!session) {
    return NextResponse.json({ error: "Unauthorized" }, { status: 401 });
  }

  try {
    // Lecture et parsing du body JSON
    const body = await req.json();
    const { url } = body; // L'URL fournie par le frontend

    if (!url) {
      return NextResponse.json(
        { error: "Missing URL in request body" },
        { status: 400 }
      );
    }

    // Construire l'URL pour l'appel backend (ex. `/academic-years`)
    const backendUrl = `${process.env.BACKEND_URL}${url}`;

    // Récupérer le token d'accès
    const accessToken = await getAccessToken();

    if (!accessToken) {
      return NextResponse.json(
        { error: "Access token is missing or expired." },
        { status: 401 }
      );
    }

    // Envoi de la requête backend
    const resp = await fetch(backendUrl, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${accessToken}`, // Ajout du Bearer Token
      },
      method: "GET", // Méthode GET par défaut pour récupérer des données
    });

    if (!resp.ok) {
      // Gérer un cas d'erreur dans la réponse backend
      const errorData = await resp.text(); // Texte brut de l'erreur backend
      console.error("Erreur backend:", errorData);
      return NextResponse.json(
        { error: errorData },
        { status: resp.status }
      );
    }

    // Récupérer les données de la réponse backend
    const data = await resp.json();
    return NextResponse.json({ data }, { status: resp.status }); // Retourne les données au client frontend

  } catch (error) {
    console.error("Erreur lors de la récupération des données:", error);
    return NextResponse.json(
      { error: "Failed to fetch data." },
      { status: 500 }
    );
  }
}