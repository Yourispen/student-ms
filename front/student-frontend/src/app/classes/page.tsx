'use client';

import { useEffect, useState } from 'react';
import ClassList from './ClassList';
import ClassForm from './ClassForm';
import { useRouter } from 'next/navigation';
import { useSession } from 'next-auth/react';

export default function ClassesPage() {
  const [refresh, setRefresh] = useState(0);
  const [selectedClass, setSelectedClass] = useState<{ id: number; name: string; description: string;} | undefined>(undefined);
  const { data: session, status } = useSession();
  const router = useRouter();

  // Redirection si l'utilisateur n'est pas authentifié
  useEffect(() => {
    if (status === 'unauthenticated') {
      router.push('/');
      router.refresh();
    }
  }, [session, status, router]);

  // Fonction pour rafraîchir la liste et réinitialiser la classe sélectionnée
  const updateList = () => {
    setRefresh((prev) => prev + 1);
    setSelectedClass(undefined); // Réinitialisation après mise à jour
  };

  return (
    <div>
      <h1 className="text-2xl font-bold">Gestion des Classes</h1>
      <ClassForm onUpdate={updateList} selectedClass={selectedClass} />
      <ClassList key={refresh} onUpdate={updateList} onEdit={setSelectedClass} />
    </div>
  );
}