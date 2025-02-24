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
    const { url, method, payload } = body; // L'URL, la méthode et les données fournies par le frontend
    console.log("payload: ", payload)

    if (!url || !method) {
      return NextResponse.json(
        { error: "Missing required fields (url or method) in the request body" },
        { status: 400 }
      );
    }

    // Construire l'URL pour l'appel backend
    const backendUrl = `${process.env.BACKEND_URL}${url}`;

    // Récupérer le token d'accès
    const accessToken = await getAccessToken();

    if (!accessToken) {
      return NextResponse.json(
        { error: "Access token is missing or expired." },
        { status: 401 }
      );
    }

    // Vérifie que la méthode est valide
    const validMethods = ["POST", "PUT", "DELETE"];
    if (!validMethods.includes(method.toUpperCase())) {
      return NextResponse.json(
        { error: `Unsupported method: ${method}` },
        { status: 400 }
      );
    }

    // Envoi de la requête backend
    const resp = await fetch(backendUrl, {
      method: method.toUpperCase(),
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
      body: JSON.stringify(payload), // Envoie les données associées (par ex., pour création/mise à jour)
    });

    // Vérifie la réponse backend
    if (!resp.ok) {
      const errorData = await resp.text(); // Retourne le message d'erreur texte
      console.error("Erreur backend:", errorData);
      return NextResponse.json(
        { error: errorData },
        { status: resp.status }
      );
    }

    // Récupérer et retourner les données de la réponse backend (le cas échéant)
    const data = await resp.json();
    return NextResponse.json({ data, success: true }, { status: resp.status });

  } catch (error) {
    console.error("Erreur lors du traitement des données :", error);
    return NextResponse.json(
      { error: "Failed to process data." },
      { status: 500 }
    );
  }
}