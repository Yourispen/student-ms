'use client';

import { useEffect, useState } from 'react';
import StudentList from './StudentList';
import StudentForm from './StudentForm';
import { useSession } from 'next-auth/react';
import { useRouter } from 'next/navigation';

export default function StudentsPage() {
  // Rafraîchir la liste des étudiants après ajout/suppression/mise à jour
  const [refresh, setRefresh] = useState(0);

  // Permet de stocker l'étudiant sélectionné pour édition
  const [selectedStudent, setSelectedStudent] = useState<
    | {
        id: number;
        firstName: string;
        lastName: string;
        emailPro: string;
        emailPerso: string;
        phoneNumber: string;
        address: string;
        token: string;
        registrationNu: string;
      }
    | undefined
  >(undefined);

  const { data: session, status } = useSession();
  const router = useRouter();

  // Redirige l'utilisateur si non connecté
  useEffect(() => {
    if (status === 'unauthenticated') {
      router.push('/');
      router.refresh();
    }
  }, [session, status, router]);

  // Met à jour la liste (rafraîchit `StudentList`) et réinitialise la sélection
  const updateList = () => {
    setRefresh((prev) => prev + 1);
    setSelectedStudent(undefined);
  };

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Gestion des Étudiants</h1>
      
      {/* Formulaire d'ajout ou de mise à jour */}
      <StudentForm 
        onUpdate={updateList} 
        selectedStudent={selectedStudent} 
      />

      {/* Liste des étudiants */}
      <StudentList 
        key={refresh} 
        onUpdate={updateList} 
        onEdit={setSelectedStudent} // Définit l'étudiant sélectionné pour édition dans le formulaire
      />
    </div>
  );
}