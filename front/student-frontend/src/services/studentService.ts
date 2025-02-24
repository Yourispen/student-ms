// Service pour gérer les API des étudiants

// Récupérer tous les étudiants
export async function getStudents() {
  try {
    const response = await fetch('/api/getData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ url: '/students' }), // L'endpoint backend pour récupérer les étudiants
    });

    if (response.ok) {
      const { data } = await response.json();
      return data; // Retourne la liste des étudiants
    } else {
      console.error("Erreur lors de la récupération des étudiants:", await response.text());
      return [];
    }
  } catch (error) {
    console.error("Erreur lors de la récupération des étudiants:", error);
    return [];
  }
}

// Récupérer un étudiant par ID
export async function getStudentById(id: number) {
  try {
    const response = await fetch('/api/getData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ url: `/students/${id}` }), // Ajouter l'ID dans l'URL
    });

    if (response.ok) {
      const { data } = await response.json();
      return data; // Retourne l'étudiant correspondant
    } else {
      console.error(`Erreur lors de la récupération de l'étudiant avec l'ID ${id}:`, await response.text());
      return null;
    }
  } catch (error) {
    console.error(`Erreur lors de la récupération de l'étudiant avec l'ID ${id}:`, error);
    return null;
  }
}

// Créer un nouvel étudiant
export async function createStudent(student: {
  firstName: string;
  lastName: string;
  emailPro: string;
  emailPerso: string;
  phoneNumber: string;
  address: string;
  token: string;
  archive: boolean;
}) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        url: '/students',
        method: 'POST', // Méthode backend pour créer un étudiant
        payload: student, // Les données de l'étudiant
      }),
    });

    if (!response.ok) {
      console.error("Erreur lors de la création de l'étudiant:", await response.text());
    } else {
      console.log("Étudiant ajouté avec succès.");
      const { data } = await response.json();
      return data;
    }
  } catch (error) {
    console.error("Erreur lors de la création de l'étudiant:", error);
    return null;
  }
}

// Mettre à jour un étudiant
export async function updateStudent(id: number, updatedStudent: {
  firstName: string;
  lastName: string;
  emailPro: string;
  emailPerso: string;
  phoneNumber: string;
  address: string;
  token: string;
  archive: boolean;
}) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        url: `/students/${id}`,
        method: 'PUT', // Méthode backend pour mettre à jour un étudiant
        payload: updatedStudent, // Les données modifiées
      }),
    });

    if (!response.ok) {
      console.error(`Erreur lors de la mise à jour de l'étudiant avec l'ID ${id}:`, await response.text());
    } else {
      console.log(`Étudiant avec ID ${id} mis à jour avec succès.`);
      const { data } = await response.json();
      return data;
    }
  } catch (error) {
    console.error(`Erreur lors de la mise à jour de l'étudiant avec l'ID ${id}:`, error);
    return null;
  }
}

// Supprimer un étudiant par ID
export async function deleteStudent(id: number) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        url: `/students/${id}`,
        method: 'DELETE', // Méthode backend pour supprimer un étudiant
        payload: {}, // Payload vide pour une suppression
      }),
    });

    if (!response.ok) {
      console.error(`Erreur lors de la suppression de l'étudiant avec l'ID ${id}:`, await response.text());
      return false;
    } else {
      console.log(`Étudiant avec ID ${id} supprimé avec succès.`);
      return true;
    }
  } catch (error) {
    console.error(`Erreur lors de la suppression de l'étudiant avec l'ID ${id}:`, error);
    return false;
  }
}