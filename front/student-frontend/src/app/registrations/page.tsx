"use client";

import { useEffect, useState } from "react";
import RegistrationList from "./RegistrationList";
import RegistrationForm from "./RegistrationForm";
import { useRouter } from "next/navigation";
import { useSession } from "next-auth/react";

export default function RegistrationsPage() {
  const [refresh, setRefresh] = useState(0);
  const [editingRegistration, setEditingRegistration] = useState<{
    id: number;
    date: string;
    description: string;
    student: {
      id: number;
      firstName: string;
      lastName: string;
      emailPro: string;
      registrationNu: string;
    };
    classId: number;
    className: string;
    academicYearId: number;
    academicYear: string;
    archive: boolean;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } | any>(undefined);

  const { data: session, status } = useSession();
  const router = useRouter();

  // Vérifie si l'utilisateur est authentifié, sinon redirige
  useEffect(() => {
    if (status === "unauthenticated") {
      router.push("/login");
    }
  }, [status, router,session]);

  // Fonction pour rafraîchir la liste après modifications
  const updateList = () => setRefresh((prev) => prev + 1);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">Gestion des Inscriptions</h1>

      {/* Formulaire d'ajout/modification */}
      <RegistrationForm
        onUpdate={updateList}
        selectedRegistration={editingRegistration}
      />

      {/* Liste des inscriptions */}
      <RegistrationList
        key={refresh} // Force le rechargement lors d’une actualisation
        onUpdate={updateList}
        onEdit={(registration) => setEditingRegistration(registration)}
      />
    </div>
  );
}
