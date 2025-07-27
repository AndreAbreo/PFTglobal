"use client";
import React, { useState, useEffect, useMemo } from "react";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";
import fetcher from "@/components/Helpers/Fetcher";

interface Pais {
  id: number;
  nombre: string;
  estado: string;
}

interface Proveedor {
  id: number;
  nombre: string;
  estado: string;
  pais: Pais;
}

const ListarProveedores: React.FC = () => {
  const [proveedores, setProveedores] = useState<Proveedor[]>([]);
  const [allProveedores, setAllProveedores] = useState<Proveedor[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const loadProveedores = async () => {
    setLoading(true);
    try {
      const data = await fetcher<Proveedor[]>("/proveedores/listar", { method: "GET" });
      setAllProveedores(data);
      setProveedores(data.filter((p) => p.estado === "ACTIVO"));
    } catch (err: any) {
      setError(err.message);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadProveedores();
  }, []);

  const handleSearch = (filters: Record<string, string>) => {
    let data = allProveedores;

    if (filters.estado) {
      data = data.filter((p) => p.estado === filters.estado);
    }

    if (filters.nombre) {
      data = data.filter((p) =>
        p.nombre.toLowerCase().includes(filters.nombre.toLowerCase())
      );
    }

    if (filters.pais) {
      data = data.filter((p) =>
        p.pais?.nombre.toLowerCase().includes(filters.pais.toLowerCase())
      );
    }

    setProveedores(data);
  };

  const columns: Column<Proveedor>[] = useMemo(() => [
    { header: "Nombre", accessor: "nombre", type: "text", filterable: true },
    {
      header: "País",
      accessor: (row) => row.pais?.nombre || "-",
      type: "text",
      filterable: true,
      filterKey: "pais",
    },
    { header: "Estado", accessor: "estado", type: "text", filterable: true }
  ], []);

  return (
    <DynamicTable
      columns={columns}
      data={proveedores}
      withFilters={true}
      onSearch={handleSearch}
      withActions={true}
      initialFilters={{ estado: "ACTIVO" }}
      deleteUrl="/proveedores/inactivar"
      basePath="/proveedores"
      confirmDeleteMessage="¿Está seguro que desea dar de baja a este proveedor?"
      sendOnlyId={true}
      onReload={loadProveedores}
    />
  );
};

export default ListarProveedores; 