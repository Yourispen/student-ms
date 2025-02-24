'use client';
import React from 'react'

import { SessionProvider } from 'next-auth/react';

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const SessionProviderWrapper = ({children}:any) => {
  return (
    <SessionProvider>{children}</SessionProvider>
  )
}

export default SessionProviderWrapper