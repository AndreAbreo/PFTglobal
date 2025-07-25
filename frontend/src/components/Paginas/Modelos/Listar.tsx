"use client";
import React, { useEffect, useState } from "react";
import fetcher from "@/components/Helpers/Fetcher";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";

interface Modelo {
  id: number;
  nombre: string;
  estado: string;
  idMarca: {
    id: number;
    nombre: string;
    estado: string;
  } | null;
}

const ListarModelos: React.FC = () => {
  const [modelos, setModelos] = useState<Modelo[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      try {
        const data = await fetcher<Modelo[]>("/modelo/filtrar", { method: "GET" });
        setModelos(data);
      } catch (err) {
        setError("Error al cargar los modelos");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  const columns: Column<Modelo>[] = [
    { header: "Nombre", accessor: "nombre", type: "text", filterable: true },
    { header: "Marca", accessor: (row) => row.idMarca?.nombre || "-", type: "text", filterable: false },
    { header: "Estado", accessor: "estado", type: "text", filterable: true },
  ];

  return (
    <>
      <h2 className="text-xl font-bold mb-4">Modelos</h2>
      {error && <p style={{ color: "red" }}>Error: {error}</p>}
      {loading ? (
        <p>Cargando...</p>
      ) : (
        <DynamicTable
          columns={columns}
          data={modelos}
          withFilters={true}
          filterUrl="/modelo/filtrar"
          onDataUpdate={setModelos}
          withActions={true}
          deleteUrl="/modelo/inactivar"
          basePath="/modelo"
          initialFilters={{ estado: "ACTIVO" }}
          sendOnlyId={true}
        />
      )}
    </>
  );
};

export default ListarModelos;
