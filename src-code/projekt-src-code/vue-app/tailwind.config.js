const defaultTheme = require("tailwindcss/defaultTheme");

module.exports = {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        sans: ["Inter var", ...defaultTheme.fontFamily.sans],
      },
      backgroundImage: {
        waves: "url('/src/assets/just-waves.png')",
        triangles: "url('/src/assets/falling-triangles.svg')",
        formal: "url('/src/assets/formal-invitation.svg')",
        circles: "url('/src/assets/overlapping-circles.svg')",
      },
    },
  },
  plugins: [],
};
