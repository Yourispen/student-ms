'use client';

import { useState, useEffect } from 'react';
import { createStudent, updateStudent } from '@/services/studentService';
import { useSession } from 'next-auth/react';

export default function StudentForm({
  onUpdate,
  selectedStudent,
}: {
  onUpdate: () => void;
  selectedStudent?: {
    id: number;
    firstName: string;
    lastName: string;
    emailPro: string;
    emailPerso: string;
    phoneNumber: string;
    address: string;
    token: string;
    registrationNu: string;
  };
}) {
  // Initialisation des données du formulaire
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    emailPro: '',
    emailPerso: '',
    phoneNumber: '',
    address: '',
    token: '',
    registrationNu: '',
    archive: false, // Case à cocher pour indiquer si l'étudiant est archivé ou non
  });
  const { data: session } = useSession();

  // Remplir le formulaire si un `selectedStudent` est fourni
  useEffect(() => {
    if (selectedStudent) {
      setFormData(
        {
        firstName: selectedStudent.firstName,
        lastName: selectedStudent.lastName,
        emailPro: selectedStudent.emailPro,
        emailPerso: selectedStudent.emailPerso,
        phoneNumber: selectedStudent.phoneNumber,
        address: selectedStudent.address,
        token: selectedStudent.token,
        registrationNu: selectedStudent.registrationNu,
        archive: false,
        }
      );
    } else {
      setFormData({
        firstName: '',
        lastName: '',
        emailPro: '',
        emailPerso: '',
        phoneNumber: '',
        address: '',
        token: '',
        registrationNu: '',
        archive: false,
      });
    }
  }, [selectedStudent]);

  // Gestion des changements sur les champs du formulaire
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: name === 'archive' ? value === 'true' : value });
  };

  // Soumettre le formulaire
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (selectedStudent) {
      // Mise à jour d'un étudiant existant
      await updateStudent(selectedStudent.id, {...formData,token:session?.access_token});
    } else {
      // Création d'un nouvel étudiant
      await createStudent({...formData,token:session?.access_token});
    }

    // Réinitialiser le formulaire après l'ajout ou la mise à jour
    setFormData({
      firstName: '',
      lastName: '',
      emailPro: '',
      emailPerso: '',
      phoneNumber: '',
      address: '',
      token: '',
      registrationNu: '',
      archive: false,
    });

    onUpdate(); // Met à jour la liste des étudiants
  };

  return (
    <form onSubmit={handleSubmit} className="mb-8">
      <input
        type="text"
        name="firstName"
        placeholder="Prénom"
        value={formData.firstName}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
        required
      />
      <input
        type="text"
        name="lastName"
        placeholder="Nom"
        value={formData.lastName}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
        required
      />
      <input
        type="email"
        name="emailPro"
        placeholder="Email Pro"
        value={formData.emailPro}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
        required
      />
      <input
        type="email"
        name="emailPerso"
        placeholder="Email Perso"
        value={formData.emailPerso}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
      />
      <input
        type="text"
        name="phoneNumber"
        placeholder="Numéro de téléphone"
        value={formData.phoneNumber}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
      />
      <input
        type="text"
        name="address"
        placeholder="Adresse"
        value={formData.address}
        onChange={handleChange}
        className="border p-2 rounded w-full mb-2"
      />
      <button
        type="submit"
        className="bg-blue-500 text-white p-2 rounded w-full hover:bg-blue-600"
      >
        {selectedStudent ? 'Mettre à jour' : 'Ajouter Étudiant'}
      </button>
    </form>
  );
}