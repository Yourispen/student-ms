'use client';

import { useEffect, useState } from 'react';
import AcademicYearList from './AcademicYearList';
import AcademicYearForm from './AcademicYearForm';
import { useSession } from 'next-auth/react';
import { useRouter } from 'next/navigation';

export default function AcademicYearsPage() {
  const [refresh, setRefresh] = useState(0);
  const [selectedYear, setSelectedYear] = useState<{ id: number; name: string; description: string } | undefined>(undefined);
  //const [errorMsg, setErrorMsg] = useState("");

  const { data: session, status } = useSession();
  const router = useRouter();

  useEffect(() => {
    if (
      status == "unauthenticated" 
    ) {
      router.push("/");
      router.refresh();
    }
  }, [session, status, router]);

  


  const updateList = () => {
    setRefresh((prev) => prev + 1);
    setSelectedYear(undefined);
  };

  return (
    <div>
      <h1 className="text-2xl font-bold">Gestion des Années Académiques</h1>
      <AcademicYearForm onUpdate={updateList} selectedYear={selectedYear} />
      <AcademicYearList key={refresh} onUpdate={updateList} onEdit={setSelectedYear} />
    </div>
  );
}
