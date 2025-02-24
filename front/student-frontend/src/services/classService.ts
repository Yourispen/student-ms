// services/classService.ts

// Récupérer toutes les classes
export async function getClasses() {
  try {
    const response = await fetch('/api/getData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ url: '/classes' }), // L'endpoint backend pour récupérer toutes les classes
    });

    if (response.ok) {
      const { data } = await response.json();
      return data; // Retourne la liste des classes
    } else {
      console.error("Erreur lors de la récupération des classes:", await response.text());
      return [];
    }
  } catch (error) {
    console.error("Erreur lors de la récupération des classes:", error);
    return [];
  }
}

// Récupérer une classe par ID
export async function getClassById(id: number) {
  try {
    const response = await fetch('/api/getData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ url: `/classes/${id}` }), // Ajouter l'ID dans l'URL
    });

    if (response.ok) {
      const { data } = await response.json();
      return data; // Retourne la classe correspondante
    } else {
      console.error(`Erreur lors de la récupération de la classe avec ID ${id}:`, await response.text());
      return null;
    }
  } catch (error) {
    console.error(`Erreur lors de la récupération de la classe avec ID ${id}:`, error);
    return null;
  }
}

// Créer une nouvelle classe
export async function createClass(cls: { name: string; description: string; archive: boolean }) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ 
        url: '/classes',
        method: 'POST', // Méthode backend pour ajouter une classe
        payload: cls, // Les données de la classe
      }),
    });

    if (!response.ok) {
      console.error("Erreur lors de l'ajout de la classe:", await response.text());
    } else {
      console.log("Classe ajoutée avec succès.");
      const { data } = await response.json();
      return data;
    }
  } catch (error) {
    console.error("Erreur lors de l'ajout de la classe:", error);
    return null;
  }
}

// Modifier une classe
export async function updateClass(id: number, updatedClass: { name: string; description: string; archive: boolean }) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        url: `/classes/${id}`,
        method: 'PUT', // Méthode backend pour mettre à jour une classe
        payload: updatedClass,
      }),
    });

    if (!response.ok) {
      console.error(`Erreur lors de la mise à jour de la classe avec ID ${id}:`, await response.text());
    } else {
      console.log(`Classe avec ID ${id} mise à jour avec succès.`);
      const { data } = await response.json();
      return data;
    }
  } catch (error) {
    console.error(`Erreur lors de la mise à jour de la classe avec ID ${id}:`, error);
    return null;
  }
}

// Supprimer une classe
export async function deleteClass(id: number) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        url: `/classes/${id}`,
        method: 'DELETE', // Méthode backend pour supprimer une classe
        payload: {}, // Payload vide pour une suppression
      }),
    });

    if (!response.ok) {
      console.error(`Erreur lors de la suppression de la classe avec ID ${id}:`, await response.text());
      return false;
    } else {
      console.log(`Classe avec ID ${id} supprimée avec succès.`);
      return true;
    }
  } catch (error) {
    console.error(`Erreur lors de la suppression de la classe avec ID ${id}:`, error);
    return false;
  }
}