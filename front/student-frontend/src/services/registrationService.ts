import { getAcademicYearById } from "./academicYearService";
import { getClassById } from "./classService";
import { getStudentById } from "./studentService";

// Récupérer toutes les inscriptions avec les détails des étudiants, classes et années académiques
export async function getRegistrations() {
  try {
    // Appelle l'API pour récupérer toutes les inscriptions
    const response = await fetch('/api/getData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ url: '/registrations' }),
    });

    if (response.ok) {
      const { data: registrations } = await response.json();

      const enrichedRegistrations = await Promise.all(
        // Enrichir chaque inscription avec les détails des étudiants, classes et années académiques
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        registrations.map(async (registration: any) => {
          const [student, classData, academicYear] = await Promise.all([
            getStudentById(registration.studentId),
            getClassById(registration.classId),
            getAcademicYearById(registration.academicYearId),
          ]);
          return {
            id: registration.id,
            date: registration.date,
            description:
              registration.description || 'Aucune description',
            student: student ?? { id: null, firstName: "Inconnu", lastName: "Inconnu", emailPro: "", registrationNu: "" },
            className: classData?.name || 'Non spécifié',
            academicYear: academicYear?.name || 'Non spécifié',
          };
        })
      );

      return enrichedRegistrations;
    } else {
      console.error("Erreur lors de la récupération des inscriptions:", await response.text());
      return [];
    }
  } catch (error) {
    console.error("Erreur lors de la récupération des inscriptions:", error);
    return [];
  }
}

// Récupérer une inscription par ID
export async function getRegistrationById(id: number) {
  try {
    // Appelle '/api/getData' pour une inscription spécifique
    const response = await fetch('/api/getData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ url: `/registrations/${id}` }),
    });

    if (response.ok) {
      const { data } = await response.json();
      return data;
    } else {
      console.error(
        `Erreur lors de la récupération de l'inscription avec ID ${id}:`,
        await response.text()
      );
      return null;
    }
  } catch (error) {
    console.error(`Erreur lors de la récupération de l'inscription avec ID ${id}:`, error);
    return null;
  }
}

// Créer une nouvelle inscription
export async function createRegistration(registration: {
  date: string;
  description: string;
  archive: boolean;
  studentId: number;
  classId: number;
  academicYearId: number;
}) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        url: '/registrations',
        method: 'POST',
        payload: registration,
      }),
    });

    if (!response.ok) {
      console.error(
        'Erreur lors de l’ajout de l’inscription:',
        await response.text()
      );
    } else {
      console.log('Inscription ajoutée avec succès.');
      const { data } = await response.json();
      return data;
    }
  } catch (error) {
    console.error('Erreur lors de l’ajout de l’inscription:', error);
    return null;
  }
}

// Modifier une inscription
export async function updateRegistration(
  id: number,
  updatedRegistration: {
    date: string;
    description: string;
    archive: boolean;
    studentId: number;
    classId: number;
    academicYearId: number;
  }
) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        url: `/registrations/${id}`,
        method: 'PUT',
        payload: updatedRegistration,
      }),
    });

    if (!response.ok) {
      console.error(
        `Erreur lors de la mise à jour de l’inscription avec ID ${id}:`,
        await response.text()
      );
    } else {
      console.log(`Inscription avec ID ${id} mise à jour avec succès.`);
      const { data } = await response.json();
      return data;
    }
  } catch (error) {
    console.error(
      `Erreur lors de la mise à jour de l’inscription avec ID ${id}:`,
      error
    );
    return null;
  }
}

// Supprimer une inscription
export async function deleteRegistration(id: number) {
  try {
    const response = await fetch('/api/setData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        url: `/registrations/${id}`,
        method: 'DELETE',
        payload: {}, // Suppression avec un payload vide
      }),
    });

    if (!response.ok) {
      console.error(
        `Erreur lors de la suppression de l’inscription avec ID ${id}:`,
        await response.text()
      );
      return false;
    } else {
      console.log(`Inscription avec ID ${id} supprimée avec succès.`);
      return true;
    }
  } catch (error) {
    console.error(
      `Erreur lors de la suppression de l’inscription avec ID ${id}:`,
      error
    );
    return false;
  }
}