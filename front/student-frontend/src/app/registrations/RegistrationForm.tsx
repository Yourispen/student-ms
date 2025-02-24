'use client';

import { useState, useEffect } from 'react';
import { createRegistration, updateRegistration } from '@/services/registrationService';
import { getStudents } from '@/services/studentService';
import { getClasses } from '@/services/classService';
import { getAcademicYears } from '@/services/academicYearService';

export default function RegistrationForm({
  onUpdate,
  selectedRegistration,
}: {
  onUpdate: () => void;
  selectedRegistration?: {
    id: number;
    date: string;
    description: string;
    studentId: number;
    classId: number;
    academicYearId: number;
  };
}) {
  // Initialisation des données du formulaire
  const [formData, setFormData] = useState({
    date: new Date().toISOString().split('T')[0],
    description: '',
    studentId: 0,
    classId: 0,
    academicYearId: 0,
  });

  const [students, setStudents] = useState<{ id: number; firstName: string; lastName: string }[]>([]);
  const [classes, setClasses] = useState<{ id: number; name: string }[]>([]);
  const [academicYears, setAcademicYears] = useState<{ id: number; name: string }[]>([]);

  // Charger les données si un `selectedRegistration` est fourni
  useEffect(() => {
    if (selectedRegistration) {
      setFormData({
        date: selectedRegistration.date,
        description: selectedRegistration.description,
        studentId: selectedRegistration.studentId,
        classId: selectedRegistration.classId,
        academicYearId: selectedRegistration.academicYearId,
      });
    } else {
      resetForm(); // Réinitialiser le formulaire si aucun enregistrement n'est sélectionné
    }
  }, [selectedRegistration]);

  // Chargement des listes pour les étudiants, classes et années académiques
  useEffect(() => {
    const fetchData = async () => {
      try {
        setStudents(await getStudents());
        setClasses(await getClasses());
        setAcademicYears(await getAcademicYears());
      } catch (error) {
        console.error('Erreur de chargement des données :', error);
      }
    };
    fetchData();
  }, []);

  // Réinitialisation des données du formulaire
  const resetForm = () => {
    setFormData({
      date: new Date().toISOString().split('T')[0],
      description: '',
      studentId: 0,
      classId: 0,
      academicYearId: 0,
    });
  };

  // Gestion des changements dans les champs du formulaire
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Soumission du formulaire
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (selectedRegistration) {
        // Mettre à jour une inscription existante
        await updateRegistration(selectedRegistration.id, { ...formData, archive: false });
      } else {
        // Créer une nouvelle inscription
        await createRegistration({ ...formData, archive: false });
      }
      onUpdate(); // Mettre à jour la liste des inscriptions
      resetForm(); // Réinitialiser le formulaire après la soumission
    } catch (error) {
      console.error('Erreur lors de l\'envoi du formulaire :', error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="mb-8">
      <h2 className="text-xl font-semibold mb-4">
        {selectedRegistration ? 'Modifier une Inscription' : 'Ajouter une Inscription'}
      </h2>

      {/* Date de l'inscription */}
      <input
        type="date"
        name="date"
        value={formData.date}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
        required
      />

      {/* Description */}
      <input
        type="text"
        name="description"
        placeholder="Description"
        value={formData.description}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
        required
      />

      {/* Liste des étudiants */}
      <select
        name="studentId"
        value={formData.studentId}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
        required
      >
        <option value="">Sélectionner un étudiant</option>
        {students.map((student) => (
          <option key={student.id} value={student.id}>
            {student.firstName} {student.lastName}
          </option>
        ))}
      </select>

      {/* Liste des classes */}
      <select
        name="classId"
        value={formData.classId}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
        required
      >
        <option value="">Sélectionner une classe</option>
        {classes.map((classItem) => (
          <option key={classItem.id} value={classItem.id}>
            {classItem.name}
          </option>
        ))}
      </select>

      {/* Liste des années académiques */}
      <select
        name="academicYearId"
        value={formData.academicYearId}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
        required
      >
        <option value="">Sélectionner une année académique</option>
        {academicYears.map((year) => (
          <option key={year.id} value={year.id}>
            {year.name}
          </option>
        ))}
      </select>

      {/* Bouton de soumission */}
      <button
        type="submit"
        className="bg-blue-500 text-white p-2 rounded w-full hover:bg-blue-600"
      >
        {selectedRegistration ? 'Mettre à jour' : 'Ajouter'}
      </button>
    </form>
  );
}