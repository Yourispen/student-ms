// Récupérer toutes les années académiques
export async function getAcademicYears() {
  try {
    const response = await fetch('/api/getData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ url: "/academic-years" }), // Passez le chemin de l'endpoint voulu
    });

    if (response.ok) {
      const { data } = await response.json(); // Récupération des données dans "data"
      return data; // Retourne toutes les années académiques
    } else {
      console.error("Erreur lors de la récupération des années académiques:", await response.text());
      return [];
    }
  } catch (error) {
    console.error("Erreur lors de la récupération des années académiques:", error);
    return [];
  }
}

// Récupérer une année académique par ID
export async function getAcademicYearById(id: number) {
  try {
    const response = await fetch(`/api/getData`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ url: `/academic-years/${id}` }), // Corrige l'URL avec un slash
    });

    if (response.ok) {
      const { data } = await response.json(); // Assumez que la réponse suit le même format structurel
      return data;
    } else {
      console.error(`Erreur lors de la récupération de l'année académique avec ID ${id}:`, await response.text());
      return null;
    }
  } catch (error) {
    console.error(`Erreur lors de la récupération de l'année académique avec ID ${id}:`, error);
    return null;
  }
}

// Créer une nouvelle année académique
export async function createAcademicYear(academicYear: { name: string; description: string; archive: boolean }) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ 
        url: "/academic-years", // URL cible pour création
        method: "POST",
        payload: academicYear, // Les données de l'année à créer sont passées comme "payload"
      }),
    });

    if (!response.ok) {
      console.error("Erreur lors de l'ajout de l'année académique:", await response.text());
      return null;
    } else {
      const { data } = await response.json();
      console.log("Année académique ajoutée avec succès.");
      return data; // Renvoie les données pour validation côté client
    }
  } catch (error) {
    console.error("Erreur lors de l'ajout de l'année académique:", error);
    return null;
  }
}

// Modifier une année académique
export async function updateAcademicYear(id: number, updatedAcademicYear: { name: string; description: string; archive: boolean }) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST', // POST pour passer par l'API intermédiaire
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        url: `/academic-years/${id}`, // URL cible pour mise à jour
        method: "PUT",
        payload: updatedAcademicYear, // Objet de mise à jour dans le payload
      }),
    });

    if (!response.ok) {
      console.error(`Erreur lors de la mise à jour de l'année académique avec ID ${id}:`, await response.text());
      return null;
    } else {
      const { data } = await response.json();
      console.log(`L'année académique avec ID ${id} a été mise à jour avec succès.`);
      return data; // Retourne les données mises à jour
    }
  } catch (error) {
    console.error(`Erreur lors de la mise à jour de l'année académique avec ID ${id}:`, error);
    return null;
  }
}

// Supprimer une année académique
export async function deleteAcademicYear(id: number) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST', // POST pour passer par l'API intermédiaire
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        url: `/academic-years/${id}`, // URL cible pour suppression
        method: "DELETE",
        payload: {}, // Pas de payload nécessaire pour cette action
      }),
    });

    if (!response.ok) {
      console.error(`Erreur lors de la suppression de l'année académique avec ID ${id}:`, await response.text());
      return false;
    } else {
      console.log(`L'année académique avec ID ${id} a été supprimée avec succès.`);
      return true; // Confirme que la suppression a réussi
    }
  } catch (error) {
    console.error(`Erreur lors de la suppression de l'année académique avec ID ${id}:`, error);
    return false;
  }
}