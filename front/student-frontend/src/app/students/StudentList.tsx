'use client';

import { useEffect, useState } from 'react';
import { getStudents, deleteStudent } from '@/services/studentService';
import { Pencil, Trash } from 'lucide-react';

export default function StudentList({
  onUpdate,
  onEdit,
}: {
  onUpdate: () => void;
  onEdit: (student: {
    id: number;
    firstName: string;
    lastName: string;
    emailPro: string;
    emailPerso: string;
    phoneNumber: string;
    address: string;
    token: string;
    registrationNu: string;
    archive: boolean;
  }) => void;
}) {
  const [students, setStudents] = useState<
    {
      id: number;
      firstName: string;
      lastName: string;
      emailPro: string;
      emailPerso: string;
      phoneNumber: string;
      address: string;
      token: string;
      registrationNu: string;
      archive: boolean;
    }[]
  >([]);

  // Charger les étudiants lors du premier rendu
  useEffect(() => {
    fetchStudents();
  }, []);

  // Fonction pour récupérer les données
  const fetchStudents = async () => {
    const data = await getStudents();
    setStudents(data);
  };

  // Supprimer un étudiant par ID
  const handleDelete = async (id: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?')) {
      await deleteStudent(id);
      onUpdate(); // Met à jour la liste après la suppression
    }
  };

  // Rendu du tableau
  return (
    <div className="overflow-x-auto">
      <h2 className="text-xl font-semibold mb-4">Liste des Étudiants</h2>
      <table className="min-w-full table-auto border-collapse border border-gray-200">
        <thead>
          <tr className="bg-gray-200">
            <th className="border border-gray-300 px-4 py-2 text-left">Prénom</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Nom</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Email Pro</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Email Perso</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Téléphone</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Adresse</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Num. Matricule</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Actions</th>
          </tr>
        </thead>
        <tbody>
          {students.map((student) => (
            <tr key={student.id} className="hover:bg-gray-100">
              <td className="border border-gray-300 px-4 py-2">{student.firstName}</td>
              <td className="border border-gray-300 px-4 py-2">{student.lastName}</td>
              <td className="border border-gray-300 px-4 py-2">{student.emailPro}</td>
              <td className="border border-gray-300 px-4 py-2">{student.emailPerso}</td>
              <td className="border border-gray-300 px-4 py-2">{student.phoneNumber}</td>
              <td className="border border-gray-300 px-4 py-2">{student.address}</td>
              <td className="border border-gray-300 px-4 py-2">{student.registrationNu}</td>
              <td className="border border-gray-300 px-4 py-2">
                <div className="flex space-x-2">
                  <button
                    onClick={() => onEdit(student)} // Passe l'étudiant sélectionné pour édition
                    className="text-blue-500 hover:text-blue-700"
                  >
                    <Pencil size={20} />
                  </button>
                  <button
                    onClick={() => handleDelete(student.id)}
                    className="text-red-500 hover:text-red-700"
                  >
                    <Trash size={20} />
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {students.length === 0 && (
        <p className="text-gray-500 text-center mt-4">Aucun étudiant trouvé.</p>
      )}
    </div>
  );
}