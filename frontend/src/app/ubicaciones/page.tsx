import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import DefaultLayout from "@/components/Layouts/DefaultLayout";

export const metadata = {
  title: "Gestión de ubicaciones",
};

export default function UbicacionesPage() {
  return (
    <DefaultLayout>
      <Breadcrumb pageName="Gestión de Ubicaciones" />
      <div className="flex flex-col gap-10">
        <h1 className="text-xl font-semibold text-black dark:text-white">
          Gestión de Ubicaciones
        </h1>
      </div>
    </DefaultLayout>
  );
}
