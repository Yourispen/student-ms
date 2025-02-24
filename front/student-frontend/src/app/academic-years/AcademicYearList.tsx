'use client';

import { useEffect, useState } from 'react';
import { getAcademicYears, deleteAcademicYear } from '@/services/academicYearService';
import { Pencil, Trash } from 'lucide-react';

export default function AcademicYearList({ onUpdate, onEdit }: { onUpdate: () => void, onEdit: (year: { id: number; name: string; description: string }) => void }) {
  const [academicYears, setAcademicYears] = useState<
    { id: number; name: string; description: string }[]
  >([]);

  useEffect(() => {
    fetchAcademicYears();
  }, []);

  const fetchAcademicYears = async () => {
    const data = await getAcademicYears();
    if (data) {
      setAcademicYears(data); // Assurez-vous que les données sont correctement structurées
    } else {
      setAcademicYears([]); // En cas d'erreur ou de données non valides
    }
  };

  const handleDelete = async (id: number) => {
    await deleteAcademicYear(id);
    onUpdate(); // Appeler la fonction de mise à jour extérieure si besoin
    fetchAcademicYears(); // Recharger les données localement
  };

  return (
    <div>
      <h2 className="text-xl font-semibold mb-4">Liste des Années Académiques</h2>
      <table className="w-full border-collapse border border-gray-300">
        <thead>
          <tr className="bg-gray-200">
            <th className="border p-2">Nom</th>
            <th className="border p-2">Description</th>
            <th className="border p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {academicYears.map((year) => (
            <tr key={year.id} className="text-center">
              <td className="border p-2">{year.name}</td>
              <td className="border p-2">{year.description}</td>
              <td className="border p-2 flex justify-center space-x-3">
                <button
                  onClick={() => onEdit(year)}
                  className="text-blue-500 hover:text-blue-700"
                >
                  <Pencil size={20} />
                </button>
                <button
                  onClick={() => handleDelete(year.id)}
                  className="text-red-500 hover:text-red-700"
                >
                  <Trash size={20} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}