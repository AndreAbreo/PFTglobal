"use client";
import React, { useState, ReactNode } from "react";
import LoginHeader from "@/components/Header/LoginHeader";

export default function LoginLayout({children,}: {children: React.ReactNode;}) {

    const [sidebarOpen, setSidebarOpen] = useState(false);
    
    return (
        <>
                {}
                <div className="relative flex flex-1 flex-col overflow-y-auto overflow-x-hidden">
                    {}
                    <LoginHeader sidebarOpen={sidebarOpen} setSidebarOpen={setSidebarOpen} />
                    {}
                    {}
                    <main>
                        <div className="mx-auto max-w-screen-2xl p-4 md:p-6 2xl:p-10">
                            {children}
                            <div className="text-center text-xs text-gray-500 py-4">
                                Realizado con ♥ por  CodigoCreativo © 2024
                            </div>
                        </div>
                    </main>
                </div>
        </>
    );
}
