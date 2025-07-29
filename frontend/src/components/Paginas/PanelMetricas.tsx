"use client";
import { useSession } from "next-auth/react";

export default function PanelMetricas() {
  const { data: session } = useSession();
  const rol = session?.user?.rol;

  if (rol !== "SuperAdmin") {
    return <div className="p-4 text-red-500">Acceso restringido.</div>;
  }

  return (
    <div className="flex flex-col gap-6">
      <section className="border p-4 rounded bg-white shadow">
        <h2 className="text-xl font-semibold mb-2">Swagger UI</h2>
        <iframe
          src="http://localhost:8080/ServidorApp-1.0-SNAPSHOT/swagger-ui/#/"
          className="w-full h-[600px] border"
          title="Swagger"
        ></iframe>
      </section>
      {/*
      <section className="border p-4 rounded bg-white shadow">
        <h2 className="text-xl font-semibold mb-2">SonarQube</h2>
        <iframe
          src="https://raw.githubusercontent.com/AndreAbreo/PFTglobal/refs/heads/AndreTest7/frontend/public/Captura%20de%20pantalla%202025-07-29%20034003.png"
          //src="http://sonar.tuempresa.local:9000/dashboard?id=proyecto-api"
          className="w-full h-[600px] border"
          title="SonarQube"
        ></iframe>
      </section>
      */}
      <section className="border p-4 rounded bg-white shadow">
        <h2 className="text-xl font-semibold mb-2">SonarQube</h2>
        
          <img src="https://raw.githubusercontent.com/AndreAbreo/PFTglobal/refs/heads/AndreTest7/frontend/public/Captura%20de%20pantalla%202025-07-29%20034003.png" />
          className="w-full h-[600px] border"
          title="SonarQube"
        
      </section>
      
      <section className="border p-4 rounded bg-white shadow">
        <h2 className="text-xl font-semibold mb-2">Dashboard Heimdall</h2>
        <iframe
          src="http://192.168.1.24:8080/"
          className="w-full h-[600px] border"
          title="Heimdall"
        ></iframe>
      </section>

      <section className="border p-4 rounded bg-white shadow">
        <h2 className="text-xl font-semibold mb-2">Vista Equipamiento</h2>
        <iframe
          src="/metricas/vista_equipamiento.html"
          className="w-full h-[600px] border"
          title="Vista Equipamiento"
        ></iframe>
      </section>

    </div>
  );
}