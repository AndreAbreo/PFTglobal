"use client";
import React, { useEffect, useState } from "react";
import fetcher from "@/components/Helpers/Fetcher";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";

interface Marca {
  id: number;
  nombre: string;
  estado: string;
}

const ListarMarcas: React.FC = () => {
  const [marcas, setMarcas] = useState<Marca[]>([]);
  const [filteredMarcas, setFilteredMarcas] = useState<Marca[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const [filters, setFilters] = useState({ nombre: "", estado: "ACTIVO" });

  const fetchMarcas = async () => {
    setLoading(true);
    try {
      const data = await fetcher<Marca[]>("/marca/listar", { method: "GET" });
      setMarcas(data);
      setFilteredMarcas(applyFilters(data, filters));
    } catch (err: any) {
      setError(err.message);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchMarcas();
  }, []);

  const applyFilters = (data: Marca[], filters: { nombre: string; estado: string }) => {
    return data.filter((marca) => {
      const matchNombre = marca.nombre.toLowerCase().includes(filters.nombre.toLowerCase());
      const matchEstado = filters.estado ? marca.estado === filters.estado : true;
      return matchNombre && matchEstado;
    });
  };

  const handleSearch = (newFilters: Record<string, string>) => {
    const updatedFilters = { ...filters, ...newFilters };
    setFilters(updatedFilters);
    setFilteredMarcas(applyFilters(marcas, updatedFilters));
  };

  const columns: Column<Marca>[] = [
    { header: "Nombre", accessor: "nombre", type: "text", filterable: true },
    { header: "Estado", accessor: "estado", type: "text", filterable: true },
  ];

  return (
    <>
      <h2 className="text-xl font-bold mb-4">Marcas</h2>
      {error && <p style={{ color: "red" }}>Error: {error}</p>}
      {loading ? (
        <p>Cargando...</p>
      ) : (
        <DynamicTable
          columns={columns}
          data={filteredMarcas}
          withFilters={true}
          onSearch={handleSearch}
          withActions={true}
          deleteUrl="/marca/inactivar"
          basePath="/marca"
          initialFilters={filters}
          sendOnlyId={true}
        />
      )}
    </>
  );
};

export default ListarMarcas;