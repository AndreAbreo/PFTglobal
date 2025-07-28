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
          src="/swagger-ui/index.html"
          className="w-full h-[600px] border"
          title="Swagger"
        ></iframe>
      </section>

      <section className="border p-4 rounded bg-white shadow">
        <h2 className="text-xl font-semibold mb-2">SonarQube</h2>
        <iframe
          src="http://sonar.tuempresa.local:9000/dashboard?id=proyecto-api"
          className="w-full h-[600px] border"
          title="SonarQube"
        ></iframe>
      </section>

      <section className="border p-4 rounded bg-white shadow">
        <h2 className="text-xl font-semibold mb-2">Reporte Power BI</h2>
        <iframe
          src="https://app.powerbi.com/view?r=XXXX"
          className="w-full h-[600px] border"
          title="PowerBI"
        ></iframe>
      </section>
    </div>
  );
}