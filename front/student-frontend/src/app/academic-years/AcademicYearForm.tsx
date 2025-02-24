'use client';

import { useState, useEffect } from 'react';
import { createAcademicYear } from '@/services/academicYearService';
import { Plus } from 'lucide-react';

export default function AcademicYearForm({ onUpdate, selectedYear }: { onUpdate: () => void, selectedYear?: { id: number; name: string; description: string } }) {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');

  useEffect(() => {
    if (selectedYear) {
      setName(selectedYear.name);
      setDescription(selectedYear.description);
    }
  }, [selectedYear]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await createAcademicYear({ name, description, archive: false });
    setName('');
    setDescription('');
    onUpdate();
  };

  return (
    <div className="mb-6">
      <h2 className="text-xl font-semibold mb-4">
        {selectedYear ? 'Modifier' : 'Ajouter'} une Année Académique
      </h2>
      <form onSubmit={handleSubmit} className="flex flex-col space-y-3">
        <input
          type="text"
          placeholder="Nom de l'année académique"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
          className="border p-2 rounded"
        />
        <input
          type="text"
          placeholder="Description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="border p-2 rounded"
        />
        <button type="submit" className="flex items-center justify-center bg-blue-600 text-white p-2 rounded">
          <Plus size={20} className="mr-2" />
          {selectedYear ? 'Modifier' : 'Ajouter'}
        </button>
      </form>
    </div>
  );
}
