"use client";
import React, { useEffect, useState } from "react";
import fetcher from "@/components/Helpers/Fetcher";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";

interface Perfil {
  id: number;
  nombrePerfil: string;
  estado: string;
}

interface Funcionalidad {
  id: number;
  nombreFuncionalidad: string;
  ruta: string;
  estado: string;
  perfiles: Perfil[];
}

const ListarFuncionalidades: React.FC = () => {
  const [funcionalidades, setFuncionalidades] = useState<Funcionalidad[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const loadData = async () => {
      setLoading(true);
      try {
        const data = await fetcher<Funcionalidad[]>("/funcionalidades/filtrar", { method: "GET" });
        setFuncionalidades(data);
      } catch (err: any) {
        setError(err.message);
      }
      setLoading(false);
    };
    loadData();
  }, []);

  const columns: Column<Funcionalidad>[] = [
    { header: "Nombre", accessor: "nombreFuncionalidad", type: "text", filterable: true },
    { header: "Ruta", accessor: "ruta", type: "text", filterable: false },
    { header: "Estado", accessor: "estado", type: "text", filterable: true },
    {
      header: "Perfiles",
      accessor: (row) => row.perfiles.map(p => p.nombrePerfil).join(", "),
      type: "text",
      filterable: true
    }
  ];

  return (
    <>
      {error && <p style={{ color: "red" }}>Error: {error}</p>}
      {loading ? (
        <p>Cargando...</p>
      ) : (
        <DynamicTable
          columns={columns}
          data={funcionalidades}
          withFilters={true}
          filterUrl="/funcionalidades/filtrar"
          onDataUpdate={setFuncionalidades}
          withActions={true}
          deleteUrl="/funcionalidades/eliminar"
          basePath="/funcionalidades"
          sendOnlyId={false}
        />
      )}
    </>
  );
};

export default ListarFuncionalidades;
