'use client';

import { useEffect, useState } from 'react';
import { getClasses, deleteClass } from '@/services/classService';
import { Pencil, Trash } from 'lucide-react';

export default function ClassList({ 
  onUpdate, 
  onEdit 
}: { 
  onUpdate: () => void, 
  onEdit: (cls: { id: number; name: string; description: string }) => void 
}) {
  const [classes, setClasses] = useState<{ id: number; name: string; description: string }[]>([]);

  // Récupère les données au chargement du composant
  useEffect(() => {
    fetchClasses();
  }, []);

  // Fonction pour récupérer les classes via le service
  const fetchClasses = async () => {
    const data = await getClasses();
    if (data) {
      setClasses(data); // Met à jour l'état avec les classes récupérées
    } else {
      setClasses([]); // Vide l'état en cas d'erreur
    }
  };

  // Fonction pour gérer la suppression d'une classe
  const handleDelete = async (id: number) => {
    await deleteClass(id); // Supprime la classe via le service
    onUpdate(); // Appelle la fonction de mise à jour parentale si nécessaire
    fetchClasses(); // Recharge localement la liste
  };

  return (
    <div>
      <h2 className="text-xl font-semibold mb-4">Liste des Classes</h2>
      <table className="w-full border-collapse border border-gray-300">
        <thead>
          <tr className="bg-gray-200">
            <th className="border p-2">Nom</th>
            <th className="border p-2">Description</th>
            <th className="border p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {classes.map((cls) => (
            <tr key={cls.id} className="text-center">
              <td className="border p-2">{cls.name}</td>
              <td className="border p-2">{cls.description}</td>
              <td className="border p-2 flex justify-center space-x-3">
                <button
                  onClick={() => onEdit(cls)} // Passe les données sélectionnées au parent
                  className="text-blue-500 hover:text-blue-700"
                >
                  <Pencil size={20} />
                </button>
                <button
                  onClick={() => handleDelete(cls.id)} // Supprime la classe sélectionnée
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