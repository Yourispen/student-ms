"use client"
import { useSession } from 'next-auth/react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

export default function Sidebar() {
  const { data: session, status } = useSession();
  const router = useRouter();

  useEffect(() => {
    if (
      status == "unauthenticated"
    ) {
      router.refresh();
    }
  }, [session, status, router]);
  if (status == "unauthenticated") {
    return null;
  }
  //console.log("session: ",session)
  return (
    <aside className="w-64 bg-gray-800 text-white h-full p-5">
      <ul>
        <li className="mb-4">
          <Link href="/students">
            <span className="hover:underline">Étudiants</span>
          </Link>
        </li>
        <li className="mb-4">
          <Link href="/classes">
            <span className="hover:underline">Classes</span>
          </Link>
        </li>
        <li className="mb-4">
          <Link href="/academic-years">
            <span className="hover:underline">Années scolaires</span>
          </Link>
        </li>
        <li className="mb-4">
          <Link href="/registrations">
            <span className="hover:underline">Inscriptions</span>
          </Link>
        </li>
      </ul>
    </aside>
  );
}
