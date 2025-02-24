'use client';

import AuthStatus from "./authStatus";


export default function Navbar() {
  return (
    <nav className="bg-blue-800 text-white p-4 flex justify-between">
      <h1 className="text-xl font-bold">Gestion Scolaire</h1>
      <div className="flex items-center space-x-4">
        {/* Intégrer AuthStatus pour gérer la logique */}
        <AuthStatus />
      </div>
    </nav>
  );
}