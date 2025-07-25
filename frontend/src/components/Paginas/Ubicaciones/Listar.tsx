"use client";
import React, { useState } from "react";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";

interface Institucion {
  id: number;
  nombre: string;
}

interface Ubicacion {
  id: number;
  nombre: string;
  sector: string;
  piso: number | null;
  numero: number | null;
  cama: number | null;
  estado: string;
  idInstitucion?: Institucion | null;
}

const ListarUbicaciones: React.FC = () => {
  const [ubicaciones, setUbicaciones] = useState<Ubicacion[]>([]);
  const [error, setError] = useState<string | null>(null);

  const columns: Column<Ubicacion>[] = [
    { header: "Nombre", accessor: "nombre", type: "text", filterable: true },
    { header: "Sector", accessor: "sector", type: "text", filterable: true },
    { header: "Piso", accessor: (row) => row.piso ?? "", type: "number", filterable: false },
    { header: "Número", accessor: (row) => row.numero ?? "", type: "number", filterable: false },
    { header: "Cama", accessor: (row) => row.cama ?? "", type: "number", filterable: false },
    { header: "Institución", accessor: (row) => row.idInstitucion?.nombre || "-", type: "text", filterable: false },
    { header: "Estado", accessor: "estado", type: "text", filterable: true },
  ];

  return (
    <>
      <h2 className="text-xl font-bold mb-4">Ubicaciones</h2>
      {error && <p style={{ color: "red" }}>Error: {error}</p>}
      <DynamicTable
        columns={columns}
        data={ubicaciones}
        withFilters={false}
        filterUrl="/ubicaciones/listar"
        onDataUpdate={setUbicaciones}
      />
    </>
  );
};

export default ListarUbicaciones;


