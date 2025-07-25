"use client";
import React, { useState } from "react";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";
import { Perfil } from "@/types/Usuario";


const Listar: React.FC = () => {
  const [perfiles, setPerfiles] = useState<Perfil[]>([]);
  const [error] = useState<string | null>(null);

  const columns: Column<Perfil>[] = [
    { header: "Nombre de Perfil", accessor: "nombrePerfil", type: "text", filterable: true, filterKey: "nombre" },
    { header: "Estado", accessor: "estado", type: "text", filterable: true, filterKey: "estado" },
  ];

  return (
    
      <>
      {error && <p style={{ color: "red" }}>Error: {error}</p>}
      <DynamicTable
        columns={columns}
        data={perfiles}
        withFilters={true}
        filterUrl="/perfiles/filtrar"
        onDataUpdate={setPerfiles}
        withActions={true}
        deleteUrl="/perfiles/inactivar"
        basePath="/perfiles"
        onDelete={async (id) => {
          return await fetcher<{ message: string }>(`/perfiles/inactivar?id=${id}`, {
            method: "PUT",
          });
        }}
      />
    </>
  );
};

export default Listar;
