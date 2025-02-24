'use client';

import { useState, useEffect } from 'react';
import { createClass, updateClass } from '@/services/classService';
import { Plus } from 'lucide-react';

export default function ClassForm({
  onUpdate,
  selectedClass,
}: {
  onUpdate: () => void;
  selectedClass?: { id: number; name: string; description: string; };
}) {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    archive: false,
  });

  // Pré-remplir le formulaire si une classe est sélectionnée
  useEffect(() => {
    if (selectedClass) {
      setFormData({
        name: selectedClass.name,
        description: selectedClass.description,
        archive: false,
      });
    } else {
      // Réinitialiser si pas de classe sélectionnée
      setFormData({
        name: '',
        description: '',
        archive: false,
      });
    }
  }, [selectedClass]);

  // Gérer les changements dans le formulaire
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: name === 'archive' ? value === 'true' : value });
  };

  // Envoyer les données pour créer ou modifier la classe
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (selectedClass) {
      // Appel de la fonction pour mettre à jour la classe sélectionnée
      await updateClass(selectedClass.id, formData);
    } else {
      // Appel de la fonction pour créer une nouvelle classe
      await createClass(formData);
    }

    // Réinitialisation du formulaire après soumission
    setFormData({
      name: '',
      description: '',
      archive: false,
    });

    onUpdate(); // Notifications au composant parent pour recharger les données
  };

  return (
    <div className="mb-6">
      <h2 className="text-xl font-semibold mb-4">
        {selectedClass ? 'Modifier' : 'Ajouter'} une Classe
      </h2>
      <form onSubmit={handleSubmit} className="flex flex-col space-y-3">
        <input
          type="text"
          name="name"
          placeholder="Nom de la classe"
          value={formData.name}
          onChange={handleChange}
          required
          className="border p-2 rounded"
        />
        <input
          type="text"
          name="description"
          placeholder="Description"
          value={formData.description}
          onChange={handleChange}
          required
          className="border p-2 rounded"
        />
        <button
          type="submit"
          className="flex items-center justify-center bg-blue-600 text-white p-2 rounded"
        >
          <Plus size={20} className="mr-2" />
          {selectedClass ? 'Modifier' : 'Ajouter'}
        </button>
      </form>
    </div>
  );
}