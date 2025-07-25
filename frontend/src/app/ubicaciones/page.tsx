import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import ListarUbicaciones from "@/components/Paginas/Ubicaciones/Listar";

export const metadata = {
  title: "Gestión de ubicaciones",
};

export default function UbicacionesPage() {
  return (
    <DefaultLayout>
      <Breadcrumb pageName="Gestión de Ubicaciones" />
      <div className="flex flex-col gap-10">
      <ListarUbicaciones />
      </div>
    </DefaultLayout>
  );
}