"use client";


import { useEffect, useState } from "react";
import { getRegistrations, deleteRegistration } from "@/services/registrationService";
import { Pencil, Trash } from "lucide-react";


interface Student {
  id: number;
  firstName: string;
  lastName: string;
  emailPro: string;
  registrationNu: string;
}


interface Registration {
  id: number;
  date: string;
  description: string;
  student: Student;
  classId: number;
  className: string;
  academicYearId: number;
  academicYear: string;
}


export default function RegistrationList({
  onUpdate,
  onEdit,
}: {
  onUpdate: () => void;
  onEdit: (registration: Registration) => void;
}) {
  const [registrations, setRegistrations] = useState<Registration[]>([]);


  useEffect(() => {
    fetchRegistrations();
  }, []);


  const fetchRegistrations = async () => {
    try {
      const data = await getRegistrations();
      setRegistrations(data);
    } catch (error) {
      console.error("Erreur lors de la récupération des inscriptions :", error);
    }
  };


  const handleDelete = async (id: number) => {
    if (confirm("Êtes-vous sûr de vouloir supprimer cette inscription ?")) {
      try {
        await deleteRegistration(id);
        onUpdate();
      } catch (error) {
        console.error("Erreur lors de la suppression :", error);
      }
    }
  };


  return (
    <div className="overflow-x-auto">
      <h2 className="text-xl font-semibold mb-4">Liste des Inscriptions</h2>
      <table className="min-w-full table-auto border-collapse border border-gray-200">
        <thead>
          <tr className="bg-gray-200">
            <th className="border border-gray-300 px-4 py-2 text-left">Étudiant</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Email Pro</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Numéro dinscription</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Date</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Classe</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Année Académique</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Description</th>
            <th className="border border-gray-300 px-4 py-2 text-left">Actions</th>
          </tr>
        </thead>
        <tbody>
          {registrations.map((registration) => (
            <tr key={registration.id} className="hover:bg-gray-100">
              <td className="border border-gray-300 px-4 py-2">
                {registration.student.firstName} {registration.student.lastName}
              </td>
              <td className="border border-gray-300 px-4 py-2">{registration.student.emailPro}</td>
              <td className="border border-gray-300 px-4 py-2">{registration.student.registrationNu}</td>
              <td className="border border-gray-300 px-4 py-2">
                {new Date(registration.date).toLocaleDateString()}
              </td>
              <td className="border border-gray-300 px-4 py-2">{registration.className}</td>
              <td className="border border-gray-300 px-4 py-2">{registration.academicYear}</td>
              <td className="border border-gray-300 px-4 py-2">{registration.description}</td>
              <td className="border border-gray-300 px-4 py-2">
                <div className="flex space-x-2">
                  <button
                    onClick={() => onEdit(registration)}
                    className="text-blue-500 hover:text-blue-700"
                  >
                    <Pencil size={20} />
                  </button>
                  <button
                    onClick={() => handleDelete(registration.id)}
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


      {registrations.length === 0 && (
        <p className="text-gray-500 text-center mt-4">Aucune inscription trouvée.</p>
      )}
    </div>
  );
}
