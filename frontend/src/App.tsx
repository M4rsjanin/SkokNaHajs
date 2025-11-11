import './index.css' // Upewnij się, że importujesz index.css (tam jest Tailwind)
import { Button } from '@/components/ui/button' // Importuj nowy przycisk (używając aliasu @)

function App() {
  return (
    // Używamy klas Tailwind do stylowania
    <div className="min-h-screen flex flex-col items-center justify-center p-4 bg-background text-foreground">
      <h1 className="text-4xl font-bold mb-6">
        Skok na Hajs (z shadcn/ui!)
      </h1>
      <div className="space-x-4">
        {/* To są Twoje nowe komponenty! */}
        <Button>Zaloguj się</Button>
        <Button variant="secondary">Zarejestruj się</Button>
        <Button variant="outline">Szczegóły</Button>
        <Button variant="destructive">Usuń</Button>
        <Button variant="link">Zapomniałem hasła</Button>
      </div>
    </div>
  )
}

export default App